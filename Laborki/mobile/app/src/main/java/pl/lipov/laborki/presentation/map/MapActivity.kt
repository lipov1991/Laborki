package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Gallery
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener {

    private val viewModel by inject<MapViewModel>()
    private lateinit var binding: ActivityMapBinding
    private lateinit var myMap: GoogleMap

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

        val galleries = listOf(
            Gallery(
                "https://play-lh.googleusercontent.com/7bGL5dRj25LtArovAdZmBQl5snL_-feJ4t3RXjEWYF7fV6DVcqv5zJgcdJrP_rH4V5g",
                "Galeria Wileńska"
            ),
            Gallery(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSMdAfMuTqiAQSOVikkVBC-7ixoWRApy_cQMKIIzcLb26KouMWKYFfLZSRZcBfPDxdDF_Y&usqp=CAU",
                "Arkadia"
            ),
            Gallery(
                "https://static1.money.pl/i/msp/znaki_towarowe/134/394364",
                "Galeria Mokotów"
            )
        )

        binding.galleries.run {
            adapter = GalleryAdapter(galleries)
            val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
            addItemDecoration(itemDecoration)
        }

        binding.bankButton.setOnClickListener {
            viewModel.changeMarker("Bank", R.drawable.bitmap_bank)
            Toast.makeText(this, viewModel.currentMarkerType, Toast.LENGTH_LONG).show()
        }

        binding.marketButton.setOnClickListener {
            viewModel.changeMarker("Shop", R.drawable.bitmap_shop)
            Toast.makeText(this, viewModel.currentMarkerType, Toast.LENGTH_LONG).show()
        }

        binding.restaurantButton.setOnClickListener {
            viewModel.changeMarker("Restaurant", R.drawable.bitmap_restaurant)
            Toast.makeText(this, viewModel.currentMarkerType, Toast.LENGTH_LONG).show()
        }

    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
        myMap = googleMap
        viewModel.setUpMap(googleMap, getString(R.string.gallety_name))
        googleMap.setOnMapLongClickListener(this)
        googleMap.setOnMarkerDragListener(this)
    }

    override fun onMapLongClick(coord: LatLng) {
        if (viewModel.mapUtils.onIndoorBuildingFocused) {
            viewModel.markerChecker().apply {
                if (this) {
                    viewModel.setMarker(
                        myMap,
                        viewModel.currentMarkerType,
                        viewModel.currentMarkerIcon,
                        coord
                    )

                } else {
                    Toast.makeText(
                        this@MapActivity,
                        "The ${viewModel.currentMarkerType} icon limit has been reached ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                this@MapActivity,
                "Markers can only be placed indoors",
                Toast.LENGTH_LONG
            ).show()
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
}