package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.customListAdapter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.repository.api.dto.GalleryDto
import pl.lipov.laborki.databinding.ActivityMapBinding


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener,
    GoogleMap.OnMarkerDragListener {

    private val viewModel: MapViewModel by viewModel()
    private val compositeDisposable = CompositeDisposable()
    private lateinit var binding: ActivityMapBinding
    private lateinit var myMap: GoogleMap

    public override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compositeDisposable.add(
            viewModel.getGalleries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.forEach {
                        viewModel.galleries.add(
                            GalleryDto(
                                it.lat,
                                it.lng,
                                it.name,
                                it.overcrowdingLevel,
                                it.url
                            )
                        )
                        Log.d("TEST", "${viewModel.galleries}")
                    }
                }, {
                    Log.d("Api Error", it.localizedMessage)
                    Toast.makeText(this, "Problem z połączeniem z internetem", Toast.LENGTH_LONG)
                        .show()
                })
        )

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        binding.floatingBtnMarket.setOnClickListener {
            viewModel.changeMarker("Sklep")
            Toast.makeText(this, viewModel.mapUtils.currentMarkerType, Toast.LENGTH_LONG).show()
        }

        binding.floatingBtnBank.setOnClickListener {
            viewModel.changeMarker("Bank")
            Toast.makeText(this, viewModel.mapUtils.currentMarkerType, Toast.LENGTH_LONG).show()
        }

        binding.floatingBtnRes.setOnClickListener {
            viewModel.changeMarker("Restauracja")
            Toast.makeText(this, viewModel.mapUtils.currentMarkerType, Toast.LENGTH_LONG).show()
        }

        binding.floatingGalleryList.setOnClickListener {
            MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                title(text = getString(R.string.title_bottom_sheet))
                customListAdapter(
                    GalleryListAdapter(
                        viewModel.galleries,
                        viewModel.mapUtils,
                        myMap
                    )
                )
            }
        }
    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
        myMap = googleMap
        viewModel.setUpMap(googleMap, getString(R.string.wilenska_galeria))
        googleMap.setOnMapLongClickListener(this)
        googleMap.setOnMarkerDragListener(this)


        // heat map

//        PoliceStationDialogFragment().show(supportFragmentManager, "PoliceStations")
    }

    override fun onMapLongClick(p0: LatLng) {
        if (viewModel.mapUtils.focusedFlag) {
            viewModel.checkMarker().apply {
                if (this) {
                    viewModel.addMarker(myMap, viewModel.mapUtils.currentMarkerType, p0)
                } else {
                    Toast.makeText(
                        this@MapActivity,
                        "Ten marker już istnieje! ",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        } else {
            Toast.makeText(
                this@MapActivity,
                "Ten marker nie jest w obrebie budynku! ",
                Toast.LENGTH_LONG
            )
                .show()
        }

    }

    override fun onBackPressed() {
        MaterialDialog(this).show {
            title(text = "Wyjscie z apki")
            message(text = "Czy na pewno")
            positiveButton(text = "Tak") {
                super.onBackPressed()
            }
            negativeButton(text = "Nie")
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onMarkerDragEnd(p0: Marker?) {}
    override fun onMarkerDragStart(p0: Marker?) {}
    override fun onMarkerDrag(p0: Marker?) {}
}