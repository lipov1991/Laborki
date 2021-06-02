package pl.lipov.laborki.common.utils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapUtils {

    fun setUpMap(
        googleMap: GoogleMap
    ) {

        val galeria = LatLng(52.2550, 21.0378)
        val warsaw = LatLng(52.2224, 21.0388)
        val cameraPosition = CameraPosition.Builder()
            .target(warsaw)
            .zoom(10f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        googleMap.setMinZoomPreference(10f)
    }
}