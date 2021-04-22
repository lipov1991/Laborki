package pl.lipov.laborki.presentation.map

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import pl.lipov.laborki.common.utils.MapUtils

class MapViewModel(
    private val mapUtils: MapUtils
) : ViewModel() {

    var markerCategory: MarkerCategory? = null
    var markerMarket: Marker? = null
    var markerRestaurant: Marker? = null
    var markerBank: Marker? = null

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
            }
        }
    }

    //usuń marker poprzez long click na okno informacyjne - work in progress
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

}
