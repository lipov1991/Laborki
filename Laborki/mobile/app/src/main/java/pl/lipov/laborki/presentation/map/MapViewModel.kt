package pl.lipov.laborki.presentation.map

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import pl.lipov.laborki.common.utils.MapUtils

class MapViewModel(
    val mapUtils: MapUtils
) : ViewModel() {
    var currentMarkerType = "Sklep"
    var markersList: MutableList<String> = mutableListOf()

    fun setUpMap(
        googleMap: GoogleMap,
        markerTitle: String
    ) {
        mapUtils.setUpMap(googleMap, markerTitle)
    }

    fun addMarker(
        googleMap: GoogleMap,
        markerTitle: String,
//        markerIcon: Int,
        coord: LatLng
    ) {
        mapUtils.addMarker(googleMap, markerTitle, coord)
    }

    fun changeMarker(
        markerType: String,
//        markerIcon: Int
    ) {
        currentMarkerType = markerType
//        currentMarkerIcon = markerIcon
    }

    fun checkMarker(): Boolean {
        if (markersList.contains(currentMarkerType)) {
            return false
        }
        markersList.add(currentMarkerType)
        return true
    }
}
