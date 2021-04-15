//package pl.lipov.laborki.presentation.map
//
//import androidx.lifecycle.ViewModel
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//
//
//class MapViewModel : ViewModel(){
//
//
//
//    fun setUpMap(
//        googleMap: GoogleMap?
//    ) {
//        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
//        val cameraPosition = CameraPosition.Builder()
//            .target(LatLng(0.0, 0.0)) // Sets the center of the map to Mountain View
//            .zoom(18f)            // Sets the zoom
//            .bearing(90f)         // Sets the orientation of the camera to east
//            .build()              // Creates a CameraPosition from the builder
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
//
//
//
//
//        googleMap.addMarker(
//            MarkerOptions()
//                .position(LatLng(0.0, 0.0))
//                .title("Marker")
//        )
//
//    }
//
//
//}