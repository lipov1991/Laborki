package pl.lipov.laborki.common.utils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapUtils {

    private val galleryLatLon = LatLng(52.2550, 21.0378)

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

//        googleMap.setOnIndoorStateChangeListener(object : GoogleMap.OnIndoorStateChangeListener {
//            override fun onIndoorBuildingFocused() {
//                TODO("Not yet implemented")
//            }
//
//            override fun onIndoorLevelActivated(indoorBuilding: IndoorBuilding) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

    fun setMarker(
        googleMap: GoogleMap,
        markerTitle: String,
        markerIcon: Int,
        coord: LatLng
    ) {
        googleMap.addMarker(
            MarkerOptions()
                .position(coord)
                .title(markerTitle)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(markerIcon))
        )
    }

//    override fun onMapReady(
//        googleMap: GoogleMap
//    ) {
//        myMap = googleMap
//        setUpMap(googleMap, "Wileniak")
//        googleMap.setOnMapLongClickListener(this)
//        googleMap.setOnMarkerDragListener(this)
//    }

//    override fun onMapLongClick(coord: LatLng) {
//        setMarker(myMap, currentMarkerType, currentMarkerColor, coord)
//    }


}
