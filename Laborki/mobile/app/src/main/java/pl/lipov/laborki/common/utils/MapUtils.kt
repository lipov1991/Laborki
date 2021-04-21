package pl.lipov.laborki.common.utils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapUtils {

    private val galleryLatLng = LatLng(52.2550, 21.0378)


    fun setUpMap(
        googleMap: GoogleMap
    ) {

        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        val cameraPosition = CameraPosition.Builder()
            .target(galleryLatLng) // Sets the center of the map to Mountain View
            .zoom(18f)            // Sets the zoom
            //.bearing(90f)         // Sets the orientation of the camera to east (orentacja kamery na wschód)
            //.tilt(30f)            // Sets the tilt of the camera to 30 degrees (kąt nachylenia kamery)
            .build()              // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        googleMap.addMarker(
            MarkerOptions()
                .position(galleryLatLng)
                .title("Galeria Wileńska")
        )

    }


}