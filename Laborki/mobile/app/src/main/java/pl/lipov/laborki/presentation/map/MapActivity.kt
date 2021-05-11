package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.listItems
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONArray
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapBinding
    private val viewModel by inject<MapViewModel>()
    private var map: GoogleMap? = null

    private var galNameList = mutableListOf<String>()
    private var galLatLngList = mutableListOf<LatLng>()

    private val warsaw = LatLng(52.229676, 21.012229)
    private var currentCat: String = "Market"
    private var marketCount: Int = 0
    private var restaurantCount: Int = 0
    private var bankCount: Int = 0
    private var isRemoving: Int = 0
    private var currentFloor: Int = 0
    private var markerArray = mutableListOf<Marker>()
    private var currentGallery = ""

    private lateinit var markerMarket: Marker
    private lateinit var markerRestaurant: Marker
    private lateinit var markerBank: Marker

    val database = Firebase.database
    val dbRef = database.getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.marketButton.visibility = View.GONE
        binding.restaurantButton.visibility = View.GONE
        binding.bankButton.visibility = View.GONE
        binding.removeButton.visibility = View.GONE
        binding.sendPlan.visibility = View.GONE

        binding.marketButton.setOnClickListener {
            currentCat = "Market"
            isRemoving = 0
        }

        binding.restaurantButton.setOnClickListener {
            currentCat = "Restaurant"
            isRemoving = 0
        }

        binding.bankButton.setOnClickListener {
            currentCat = "Bank"
            isRemoving = 0
        }

        binding.sendPlan.setOnClickListener {
            if (markerArray.count() == 0) {
                Toast.makeText(this, "Brak punktów do wysłania!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Plan zagospodarowania został wysłany na serwer!", Toast.LENGTH_SHORT).show()
                clear()
            }
        }

        binding.removeButton.setOnClickListener {
            isRemoving = 1
        }

        dbRef.child("galeries").get().addOnSuccessListener {
            var array = JSONArray(Gson().toJson(it.value))
            for (i in 0 until array.length()) {
                var name = array.getJSONObject(i).get("name")
                var lat = array.getJSONObject(i).get("lat")
                var lng = array.getJSONObject(i).get("lng")
                galNameList.add(name as String)
                galLatLngList.add(LatLng(lat as Double, lng as Double))
            }
        }
    }

    private fun clear() {
        for (marker in markerArray) {
            marker.remove()
        }
        markerArray.clear()
        marketCount = 0
        restaurantCount = 0
        bankCount = 0
        currentCat = "Market"
    }

    override fun onBackPressed() {
        if (markerArray.count() != 0) {
            MaterialDialog(this).show {
                title(text = "Wyjście z aplikacji")
                message(text = "Czy na pewno chcesz zakończyć plan zagospodarowania bez wysłania go na serwer?")
                positiveButton(text = "Tak") { dialog ->
                    dialog.dismiss()
                    super.onBackPressed()
                }
                negativeButton(text = "Nie") { dialog ->
                    dialog.dismiss()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (map != null) {
            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(warsaw, 15f))

            binding.placeButton.setOnClickListener {
                if (markerArray.count() != 0) {
                    MaterialDialog(this).show {
                        title(text = currentGallery)
                        message(text = "Czy na pewno chcesz zakończyć plan zagospodarowania bez wysłania go na serwer?")
                        positiveButton(text = "Tak") { dialog ->
                            clear()
                            dialog.dismiss()
                        }
                        negativeButton(text = "Nie") { dialog ->
                            dialog.dismiss()
                        }
                    }
                } else if (markerArray.count() == 0) {
                    MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                        listItems(items = galNameList) { _, index, _ ->
                            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(galLatLngList[index], 18f), 2000, null)
                            currentGallery = galNameList[index]
                            clear()
                        }
                    }
                }
            }

            map!!.setOnMapLongClickListener {
                if (currentCat == "Market" && marketCount == 0) {
                    markerMarket = googleMap.addMarker(
                            MarkerOptions().position(it).title(currentCat).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    )
                    markerMarket.tag = currentFloor
                    markerArray.add(markerMarket)
                    marketCount += 1
                }
                if (currentCat == "Restaurant" && restaurantCount == 0) {
                    markerRestaurant = googleMap.addMarker(
                            MarkerOptions().position(it).title(currentCat).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    )
                    markerRestaurant.tag = currentFloor
                    markerArray.add(markerRestaurant)
                    restaurantCount += 1
                }
                if (currentCat == "Bank" && bankCount == 0) {
                    markerBank = googleMap.addMarker(
                            MarkerOptions().position(it).title(currentCat).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
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
                    binding.sendPlan.visibility = View.GONE
                } else {
                    binding.marketButton.visibility = View.VISIBLE
                    binding.restaurantButton.visibility = View.VISIBLE
                    binding.bankButton.visibility = View.VISIBLE
                    binding.removeButton.visibility = View.VISIBLE
                    binding.sendPlan.visibility = View.VISIBLE
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