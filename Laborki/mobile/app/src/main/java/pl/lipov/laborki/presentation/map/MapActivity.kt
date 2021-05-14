package pl.lipov.laborki.presentation.map

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.data.repository.api.dto.GalleriesDto
import pl.lipov.laborki.databinding.ActivityMapBinding
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private val viewModel by inject<MapViewModel>()
    private lateinit var binding: ActivityMapBinding
    private lateinit var myMap: GoogleMap
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(
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
                        viewModel.galleryList.add(
                            GalleriesDto(
                                it.lat,
                                it.lng,
                                it.name,
                                it.overcrowdingLevel,
                                it.url
                            )
                        )
                        Log.d("TEST", "${viewModel.galleryList}")
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


        viewModel.rotationChange.observe(::getLifecycle) { rotation ->
            binding.compass.rotation = rotation
        }
        viewModel.setUpCompass()

        binding.mpzpButton.setOnClickListener {
            viewModel.mapUtils.sentAreaDevelopmentPlan(this, myMap)
        }

        binding.bankButton.setOnClickListener {
            viewModel.changeMarker("Bank", R.drawable.bitmap_bank)
            Toast.makeText(this, viewModel.mapUtils.currentMarkerType, Toast.LENGTH_LONG).show()
        }

        binding.marketButton.setOnClickListener {
            viewModel.changeMarker("Shop", R.drawable.bitmap_shop)
            Toast.makeText(this, viewModel.mapUtils.currentMarkerType, Toast.LENGTH_LONG).show()
        }

        binding.restaurantButton.setOnClickListener {
            viewModel.changeMarker("Restaurant", R.drawable.bitmap_restaurant)
            Toast.makeText(this, viewModel.mapUtils.currentMarkerType, Toast.LENGTH_LONG).show()
        }

    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
        myMap = googleMap
        viewModel.setUpMap(googleMap, this)
        googleMap.setOnMarkerDragListener(this)
        addHeatMap(googleMap, this)
        binding.bottomSheetButton.setOnClickListener {
            MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                title(text = getString(R.string.bottom_sheet_title))
                customListAdapter(
                    GalleryAdapter(
                        viewModel.galleryList,
                        viewModel.mapUtils,
                        googleMap,
                        this@MapActivity
                    )
                )
            }
        }
    }

    override fun onBackPressed() {
        MaterialDialog(this).show {
            title(text = getString(R.string.on_press_back_title))
            message(text = getString(R.string.on_press_back_msg))
            positiveButton(text = getString(R.string.on_press_back_positive_btn)) {
                super.onBackPressed()
            }
            negativeButton(text = getString(R.string.on_press_back_negative_btn))
        }
    }

    override fun onMarkerDragEnd(marker: Marker) {

        val markerScreenPosition = myMap.projection.toScreenLocation(marker.position)

        if (viewModel.overlap(markerScreenPosition, binding.imgDelete)) {
            viewModel.deleteMarker(marker.title)
            Toast.makeText(
                this@MapActivity,
                "The marker has been removed",
                Toast.LENGTH_LONG
            ).show()
            marker.remove()
        }

        binding.bankButton.visibility = View.VISIBLE
        binding.marketButton.visibility = View.VISIBLE
        binding.restaurantButton.visibility = View.VISIBLE
        binding.imgDelete.visibility = View.INVISIBLE
    }

    override fun onMarkerDragStart(p0: Marker?) {
        binding.bankButton.visibility = View.INVISIBLE
        binding.marketButton.visibility = View.INVISIBLE
        binding.restaurantButton.visibility = View.INVISIBLE
        binding.imgDelete.visibility = View.VISIBLE
    }

    override fun onMarkerDrag(p0: Marker) {
    }

    private fun addHeatMap(map: GoogleMap, context: Context) {
        try {
            val galleries = viewModel.galleryList
            val weightedLatLngs = galleries.map {
                val latLng = LatLng(it.lat, it.lng)
                WeightedLatLng(latLng, it.overcrowdingLevel.toDouble())
            }
            val provider = HeatmapTileProvider.Builder()
                .weightedData(weightedLatLngs)
                .build()
            map.addTileOverlay(TileOverlayOptions().tileProvider(provider))
        } catch (e: JSONException) {
            Toast.makeText(context, "Problem reading list of locations.", Toast.LENGTH_LONG)
                .show()
        }
    }
}