package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.repository.api.dto.GalleryDto
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModel()
    private lateinit var binding: ActivityMapBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.fab1.setOnClickListener {
            viewModel.markerCategory = MarkerCategory.market
        }
        binding.fab2.setOnClickListener {
            viewModel.markerCategory = MarkerCategory.restauracja
        }
        binding.fab3.setOnClickListener {
            viewModel.markerCategory = MarkerCategory.bank
        }

        compositeDisposable.add(
            viewModel.getGalleries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    list.forEach {
                        viewModel.galleries.add(
                            GalleryDto(
                                it.lat,
                                it.lng,
                                it.name,
                                it.overcrowdingLevel,
                                it.url
                            )
                        )
                    }
                }, {
                    Toast.makeText(this, "Problem z połączeniem", Toast.LENGTH_LONG)
                        .show()
                })
        )
    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
        viewModel.setUpMap(googleMap)
        viewModel.addLocation(googleMap)
        viewModel.removeMarker(googleMap)
        viewModel.indoorBuildingMarkerManagement(
            googleMap,
            binding.fab1,
            binding.fab2,
            binding.fab3
        )
        viewModel.showGalleryList(this, binding.fab4, googleMap)
        viewModel.sendBuildingPlans(this, binding.fab5)
    }

    override fun onBackPressed() {
        MaterialDialog(this).show {
            title(R.string.app_exit_title)
            message(R.string.app_exit_message)
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
}


