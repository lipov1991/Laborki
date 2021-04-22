package pl.lipov.laborki.presentation.map

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import pl.lipov.laborki.common.utils.MapUtils

class MapViewModel(
    private val mapUtils: MapUtils

) : ViewModel() {

    var markercategory: MarkerCategory? = null
    var markermarket: Marker? = null
    var markerrestauracja: Marker? = null
    var markerbank: Marker? = null

    var focusedMarker: Marker? = null

    fun setUpMap(
        googleMap: GoogleMap,
        context: Context
    ) {
       mapUtils.setUpMap(googleMap,context)
    }


    fun checkMarkerGallery(marker: Marker?):Boolean{
        if(marker == mapUtils.markerGallery) return true

        return false
    }



    fun addMarket(
        googleMap: GoogleMap
    ){
        googleMap.setOnMapLongClickListener { latLng ->



            if(markercategory == MarkerCategory.Market) {
                if(markermarket != null)
                {
                    markermarket?.remove()
                }
                markermarket = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(markercategory.toString())
                        .draggable(true) // allows to drag & drop marker
                )
            }

            if(markercategory == MarkerCategory.Restauracja) {

                if(markerrestauracja != null)
                {
                    markerrestauracja?.remove()
                }
                markerrestauracja = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(markercategory.toString())
                        .draggable(true) // allows to drag & drop marker
                )
            }

            if(markercategory == MarkerCategory.Bank) {
                if(markerbank != null)
                {
                    markerbank?.remove()
                }
                markerbank = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(markercategory.toString())
                        .draggable(true) // allows to drag & drop marker
                )
            }

        }
    }

}