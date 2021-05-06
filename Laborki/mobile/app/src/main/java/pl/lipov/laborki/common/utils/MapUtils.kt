package pl.lipov.laborki.common.utils

import android.content.Context
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import pl.lipov.laborki.R

class MapUtils {

    private val warsawLatLon = LatLng(52.2370, 21.0175)
    var currentIndoorLevel = -1
    var onIndoorBuildingFocused = false
    var currentMarkerType = "Shop"
    var currentMarkerIcon = R.drawable.bitmap_shop
    var markersList: MutableList<Pair<Marker, Int>> = mutableListOf()

    fun setUpMap(
        googleMap: GoogleMap,
        context: Context
    ) {

        val cameraPosition = CameraPosition.Builder()
            .target(warsawLatLon)
            .zoom(10f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        googleMap.setOnIndoorStateChangeListener(object : GoogleMap.OnIndoorStateChangeListener {
            override fun onIndoorBuildingFocused() {
                onIndoorBuildingFocused = googleMap.focusedBuilding != null

                if (!onIndoorBuildingFocused) {
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

        googleMap.setOnMapLongClickListener(object : GoogleMap.OnMapLongClickListener {
            override fun onMapLongClick(coord: LatLng) {
                if (onIndoorBuildingFocused) {
                    markerChecker().apply {
                        if (this) {
                            setMarker(
                                googleMap,
                                currentMarkerType,
                                currentMarkerIcon,
                                coord
                            )

                        } else {
                            Toast.makeText(
                                context,
                                "The ${currentMarkerType} icon limit has been reached ",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Markers can only be placed indoors",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    fun navToGallery(
        googleMap: GoogleMap,
        markerTitle: String,
        galleryLatLon: LatLng
    ) {

        val cameraPosition = CameraPosition.Builder()
            .target(galleryLatLon)
            .zoom(18f)
            .build()

        currentMarkerType = "Shop"
        currentMarkerIcon = R.drawable.bitmap_shop
        markersList.clear()
        googleMap.clear()

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        googleMap.addMarker(
            MarkerOptions()
                .position(galleryLatLon)
                .title(markerTitle)
        )
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

    fun markerChecker(): Boolean {
        if (markersList
                .filter { (it.second == currentIndoorLevel) and (it.first.title == currentMarkerType) }
                .isEmpty()
        ) {
            return true
        }
        return false
    }

}
