package pl.lipov.laborki.presentation.map

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*

class MapViewModel : ViewModel() {

    private val placeCoordinates = LatLng(52.2550, 21.0378)
    var categoryMarker : String? = null
    var market : Marker? = null
    var food : Marker?=null
    var bank : Marker?= null
    var pin : Marker? = null
    var pin_gallery : Marker? = null

    fun setUpMap(
        googleMap: GoogleMap
    )
    {
        val cameraPosition = CameraPosition.Builder()
            .target(placeCoordinates)
            .zoom(18f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

       pin_gallery= googleMap.addMarker(
            MarkerOptions()
                .position(placeCoordinates)
                .title("Galeria Wileńska")
        )


    }

    fun setMarker(
        map: GoogleMap,
    ){
        map.setOnMapLongClickListener { latLng ->
            if (categoryMarker == "market") {
                if (market != null) {
                    market?.remove()
                }
                market = map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(categoryMarker)
                        .draggable(true)

                )
            }


            if (categoryMarker == "restauracja") {
                if (food != null) {
                    food?.remove()
                }
                food = map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(categoryMarker)
                        .draggable(true)

                )
            }

            if(categoryMarker=="bank"){
                if(bank!=null){
                    bank?.remove()
                }
                    bank = map.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title(categoryMarker)
                            .draggable(true)

                    )
                }

        }

    }



}