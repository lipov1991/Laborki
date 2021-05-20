package pl.lipov.laborki.common.utils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class MapUtils {
    private val galleryLatLon = LatLng(52.2550, 21.0378)

    var focusedFlag = false
    var currentLevel = -1
    var currentMarkerType = "Sklep"

    var pinsList: MutableList<Pair<Marker, Int>> = mutableListOf()
    var areaButttonAlreadyPressed = false

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
                focusedFlag = googleMap.focusedBuilding != null

                if (!focusedFlag) {
                    pinsList.forEach {
                        it.first.isVisible = false
                    }
                } else {
                    pinsList.forEach {
                        it.first.isVisible = it.second == currentLevel
                    }
                }
            }

            override fun onIndoorLevelActivated(p0: IndoorBuilding) {
                currentLevel = p0.activeLevelIndex

                pinsList.forEach {
                    it.first.isVisible = it.second == currentLevel
                }
            }

        })

    }

    fun addMarker(
        googleMap: GoogleMap,
        markerTitle: String,
        coord: LatLng
    ) {
        val googleMarkers = googleMap.addMarker(
            MarkerOptions()
                .position(coord)
                .title(markerTitle)
                .draggable(true)
        )

        pinsList.add(Pair(googleMarkers, currentLevel))


    }

    fun setUpGallery(
        googleMap: GoogleMap,
        markerName: String,
        latLonGallery: LatLng
    ) {

        val cameraPosition = CameraPosition.Builder()
            .target(latLonGallery)
            .zoom(18f)
            .build()

        currentMarkerType = "Sklep"
        pinsList.forEach { it.first.remove() }
        pinsList.clear()

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        googleMap.addMarker(
            MarkerOptions()
                .position(latLonGallery)
                .title(markerName)
        )
    }
}
