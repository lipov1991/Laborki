package pl.lipov.laborki.presentation.map

import android.graphics.Point
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import pl.lipov.laborki.R
import pl.lipov.laborki.common.utils.MapUtils

class MapViewModel(
    val mapUtils: MapUtils
) : ViewModel() {

    var currentMarkerType = "Shop"
    var currentMarkerIcon = R.drawable.bitmap_shop

    fun deleteMarker(
        markerType: String
    ) {
        mapUtils.markersList.removeAll { (it.second == mapUtils.currentIndoorLevel) and (it.first.title == markerType) }
    }

    fun markerChecker(): Boolean {
        if (mapUtils.markersList
                .filter { (it.second == mapUtils.currentIndoorLevel) and (it.first.title == currentMarkerType) }
                .isEmpty()
        ) {
            return true
        }
        return false
    }

    fun changeMarker(
        markerType: String,
        markerIcon: Int
    ) {
        currentMarkerType = markerType
        currentMarkerIcon = markerIcon
    }

    fun setUpMap(
        googleMap: GoogleMap,
        markerTitle: String
    ) {
        mapUtils.setUpMap(googleMap, markerTitle)
    }

    fun setMarker(
        googleMap: GoogleMap,
        markerTitle: String,
        markerIcon: Int,
        coord: LatLng
    ) {
        mapUtils.setMarker(googleMap, markerTitle, markerIcon, coord)
    }

    fun overlap(point: Point, imgview: ImageView): Boolean {
        val imgCoords = IntArray(2)
        imgview.getLocationOnScreen(imgCoords)
        val overlapX =
            point.x < imgCoords[0] + imgview.getWidth() && point.x > imgCoords[0] - imgview.getWidth()
        val overlapY =
            point.y < imgCoords[1] + imgview.getHeight() && point.y > imgCoords[1] - imgview.getWidth()
        return overlapX && overlapY
    }

}