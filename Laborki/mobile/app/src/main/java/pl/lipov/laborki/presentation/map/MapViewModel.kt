package pl.lipov.laborki.presentation.map

import android.view.View
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.IndoorBuilding
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pl.lipov.laborki.common.utils.MapUtils

class MapViewModel(
    private val mapUtils: MapUtils
) : ViewModel() {

    var markerCategory: MarkerCategory? = null
    var markerMarket: Marker? = null
    var markerRestaurant: Marker? = null
    var markerBank: Marker? = null
    private var markerList = mutableListOf<Marker>()
    private var currentFloor: Int = 0

    fun setUpMap(
        googleMap: GoogleMap
    ) {
        mapUtils.setUpMap(googleMap)
    }

    fun addLocation(
        googleMap: GoogleMap
    ) {

        googleMap.setOnMapLongClickListener { latLng ->

            if (markerCategory == MarkerCategory.market) {
                if (markerMarket != null) {
                    markerMarket?.remove()
                }
                markerMarket = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(markerCategory.toString())
                        .draggable(true)
                )
                    markerMarket!!.tag = currentFloor
                    markerList.add(markerMarket!!)
            }

            if (markerCategory == MarkerCategory.restauracja) {

                if (markerRestaurant != null) {
                    markerRestaurant?.remove()
                }
                markerRestaurant = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(markerCategory.toString())
                        .draggable(true)
                )
                    markerRestaurant!!.tag = currentFloor
                    markerList.add(markerRestaurant!!)
            }

            if (markerCategory == MarkerCategory.bank) {
                if (markerBank != null) {
                    markerBank?.remove()
                }
                markerBank = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(markerCategory.toString())
                        .draggable(true)
                )
                    markerBank!!.tag = currentFloor
                    markerList.add(markerBank!!)
            }
        }
    }

    fun removeMarker(
        googleMap: GoogleMap
    ) {
        googleMap.setOnInfoWindowLongClickListener {
            if (markerCategory == MarkerCategory.market)
                markerMarket?.remove()
            if (markerCategory == MarkerCategory.restauracja)
                markerRestaurant?.remove()
            if (markerCategory == MarkerCategory.bank)
                markerBank?.remove()
        }
    }

    fun indoorBuildingMarkerManagement(
        googleMap: GoogleMap,
        marketButton: FloatingActionButton?,
        restaurantButton: FloatingActionButton?,
        bankButton: FloatingActionButton?
    ) {

        googleMap.setOnIndoorStateChangeListener(object : GoogleMap.OnIndoorStateChangeListener {

            override fun onIndoorBuildingFocused() {
                googleMap.setOnCameraMoveListener {
                    if (googleMap.focusedBuilding == null) {
                        marketButton?.visibility = View.GONE
                        restaurantButton?.visibility = View.GONE
                        bankButton?.visibility = View.GONE
                    } else {
                        marketButton?.visibility = View.VISIBLE
                        restaurantButton?.visibility = View.VISIBLE
                        bankButton?.visibility = View.VISIBLE
                    }
                }
            }

            override fun onIndoorLevelActivated(p0: IndoorBuilding) {
                val levels = p0.levels
                val activeLevel = p0.activeLevelIndex
                currentFloor = levels[activeLevel].name.toInt()

                for (m: Marker in markerList) {
                    if (m.tag != currentFloor) {
                        m.isVisible = false
                    } else {
                        m.isVisible = true
                    }
                }
            }
        })
    }
}
