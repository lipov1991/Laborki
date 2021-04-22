package pl.lipov.laborki.presentation.map

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapViewModel : ViewModel() {
    private val placeCoordinates = LatLng(52.2550, 21.0378)

    fun setUpMap(
        googleMap: GoogleMap
    )
    {
        val cameraPosition = CameraPosition.Builder()
            .target(placeCoordinates) // Sets the center of the map to Mountain View
            .zoom(18f)            // Sets the zoom
            .build()              // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        googleMap.addMarker(
            MarkerOptions()
                .position(placeCoordinates)
                .title("Galeria Wileńska")
        )


    }

}

