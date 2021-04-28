package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.IndoorBuilding
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapActivity: AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapBinding
    private val viewModel by inject<MapViewModel>()
    private var map: GoogleMap? = null
    private val galWilenska = LatLng(52.2550, 21.0378)
    private val warsaw = LatLng(52.229676, 21.012229)
    private var cat: String = "Bank"
    private var marketCount: Int = 0
    private var restaurantCount: Int = 0
    private var bankCount: Int = 0
    private var isRemoving: Int = 0
    private var currentFloor: Int = 0
    private var markerArray = mutableListOf<Marker>()

    private lateinit var markerMarket: Marker
    private lateinit var markerRestaurant: Marker
    private lateinit var markerBank: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.marketButton.setOnClickListener {
            cat = "Market"
            isRemoving = 0
        }

        binding.restaurantButton.setOnClickListener {
            cat = "Restaurant"
            isRemoving = 0
        }

        binding.bankButton.setOnClickListener {
            cat = "Bank"
            isRemoving = 0
        }

        binding.removeButton.setOnClickListener {
            isRemoving = 1
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (map != null) {
            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(warsaw, 15f))
            map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(galWilenska, 18f), 2000, null)

            googleMap.addMarker(
                MarkerOptions()
                    .position(galWilenska)
                    .title("Galeria Wileńska")
            ).showInfoWindow()

            map!!.setOnMapLongClickListener {
                if (cat == "Market" && marketCount == 0) {
                    markerMarket = googleMap.addMarker(
                            MarkerOptions().position(it).title(cat).draggable(true)
                    )
                    markerMarket.tag = currentFloor
                    markerArray.add(markerMarket)
                    marketCount += 1
                }
                if (cat == "Restaurant" && restaurantCount == 0) {
                    markerRestaurant = googleMap.addMarker(
                            MarkerOptions().position(it).title(cat).draggable(true)
                    )
                    markerRestaurant.tag = currentFloor
                    markerArray.add(markerRestaurant)
                    restaurantCount += 1
                }
                if (cat == "Bank" && bankCount == 0) {
                    markerBank = googleMap.addMarker(
                            MarkerOptions().position(it).title(cat).draggable(true)
                    )
                    markerBank.tag = currentFloor
                    markerArray.add(markerBank)
                    bankCount += 1
                }

            }

            map!!.setOnMarkerClickListener { marker ->
                if (isRemoving == 1) {
                    if (marker.title == "Market") {
                        marketCount = 0
                    }
                    if (marker.title == "Restaurant") {
                        restaurantCount = 0
                    }
                    if (marker.title == "Bank") {
                        bankCount = 0
                    }
                    marker.remove()
                } else {
                    marker.showInfoWindow()
                }
                true
            }

            map!!.setOnCameraMoveListener() {
                if (googleMap.focusedBuilding == null) {
                    binding.marketButton.visibility = View.GONE
                    binding.restaurantButton.visibility = View.GONE
                    binding.bankButton.visibility = View.GONE
                    binding.removeButton.visibility = View.GONE
                } else {
                    binding.marketButton.visibility = View.VISIBLE
                    binding.restaurantButton.visibility = View.VISIBLE
                    binding.bankButton.visibility = View.VISIBLE
                    binding.removeButton.visibility = View.VISIBLE
                }
            }

            googleMap.setOnIndoorStateChangeListener(object : GoogleMap.OnIndoorStateChangeListener {

                override fun onIndoorBuildingFocused() {}

                override fun onIndoorLevelActivated(indoorBuilding: IndoorBuilding) {
                    val levels = indoorBuilding.levels

                    val level = indoorBuilding.activeLevelIndex
//                    Log.d("Tag", "Level Index: $level") // For testing
                    currentFloor = levels[level].name.toInt()
//                    Log.d("Tag", "Level: $currentFloor") // For testing

                    for (marker: Marker in markerArray) {
                        if (marker.tag != currentFloor) {
                            marker.isVisible = false
                        } else {
                            marker.isVisible = true
                        }
                    }
                }
            })
        }
    }
}