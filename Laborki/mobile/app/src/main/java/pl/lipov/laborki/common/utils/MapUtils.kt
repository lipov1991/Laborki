package pl.lipov.laborki.common.utils

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.IndoorBuilding
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapUtils {

    fun setUpMap(googleMap: GoogleMap) {

        val galeria = LatLng(52.2550, 21.0378)
        val cameraPosition = CameraPosition.Builder()
            .target(galeria)
            .zoom(18f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        googleMap.addMarker(
            MarkerOptions()
                .position(galeria)
                .title("Galeria Wileńska"))

//        googleMap.setOnIndoorStateChangeListener(object: GoogleMap.OnIndoorStateChangeListener {
//
//            override fun onIndoorBuildingFocused() {
//                TODO("Not yet implemented")
//            }
//
//            override fun onIndoorLevelActivated(p0: IndoorBuilding?) {
//                TODO("Not yet implemented")
//            }
//        })
    }

}