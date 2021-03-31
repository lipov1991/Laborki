package pl.lipov.laborki.presentation.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
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
                    googleMap.addMarker(
                            MarkerOptions().position(it).title(cat).draggable(true)
                    )
                    marketCount += 1
                }
                if (cat == "Restaurant" && restaurantCount == 0) {
                    googleMap.addMarker(
                            MarkerOptions().position(it).title(cat).draggable(true)
                    )
                    restaurantCount += 1
                }
                if (cat == "Bank" && bankCount == 0) {
                    googleMap.addMarker(
                            MarkerOptions().position(it).title(cat).draggable(true)
                    )
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
        }
    }
}