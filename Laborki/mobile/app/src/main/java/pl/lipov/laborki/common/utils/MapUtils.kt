package pl.lipov.laborki.common.utils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class MapUtils {

    private val galleryLatLon = LatLng(52.2550, 21.0378)
    var currentIndoorLevel = -1
    var onIndoorBuildingFocused = false
    var markersList: MutableList<Pair<Marker, Int>> = mutableListOf()

    fun setUpMap(
        googleMap: GoogleMap,
        markerTitle: String,
    ) {

        val cameraPosition = CameraPosition.Builder()
            .target(galleryLatLon)
            .zoom(18f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        googleMap.addMarker(
            MarkerOptions()
                .position(galleryLatLon)
                .title(markerTitle)
        )

        googleMap.setOnIndoorStateChangeListener(object : GoogleMap.OnIndoorStateChangeListener {
            override fun onIndoorBuildingFocused() {
                onIndoorBuildingFocused = googleMap.focusedBuilding != null

                if (onIndoorBuildingFocused == false) {
                    markersList.forEach {
                        it.first.isVisible = false
                    }
                } else {
                    checkIndoorLevel()
                }
            }

            override fun onIndoorLevelActivated(indoorBuilding: IndoorBuilding) {
                currentIndoorLevel = indoorBuilding.activeLevelIndex
                checkIndoorLevel()
            }
        })
    }

    fun setMarker(
        googleMap: GoogleMap,
        markerTitle: String,
        markerIcon: Int,
        coord: LatLng
    ) {
        val marker = googleMap.addMarker(
            MarkerOptions()
                .position(coord)
                .title(markerTitle)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(markerIcon))
        )
        markersList.add(Pair(marker, currentIndoorLevel))
    }

    fun checkIndoorLevel() {
        markersList.forEach {
            it.first.isVisible = it.second == currentIndoorLevel
        }
    }
}
