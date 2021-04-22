package pl.lipov.laborki.common.utils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.IndoorBuilding
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapUtils {
    private val galleryLatLon = LatLng(52.2550, 21.0378)

    var focusedFlag = false

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
    }

    fun addMarker(
        googleMap: GoogleMap,
        markerTitle: String,
//        markerIcon: Int,
        coord: LatLng
    ) {
        googleMap.addMarker(
            MarkerOptions()
                .position(coord)
                .title(markerTitle)
                .draggable(true)
//                .icon(BitmapDescriptorFactory.fromResource(markerIcon))
        )

        googleMap.setOnIndoorStateChangeListener(object : GoogleMap.OnIndoorStateChangeListener {
            override fun onIndoorBuildingFocused() {
                focusedFlag = !focusedFlag

                //print toast testowo costamcostam
                // tutaj wszystko nalezy zaimplemetowac do zad 5
            }

            override fun onIndoorLevelActivated(p0: IndoorBuilding?) {

                //print toast testowo costamcostam
            }

        })
    }
}
