package pl.lipov.laborki.presentation.map

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import io.reactivex.internal.operators.completable.CompletableResumeNext
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapViewModel : ViewModel() {

    private val placeCoordinates = LatLng(52.2550, 21.0378)
    var categoryMarker : String? = null
    var market : Marker? = null
    var food : Marker?=null
    var bank : Marker?= null
    var pin : Marker? = null
    var pin_gallery : Marker? = null

    var marketL0 : Marker? = null
    var foodL0 : Marker? = null
    var bankL0 : Marker? = null

    var marketL1 : Marker? = null
    var foodL1 : Marker? = null
    var bankL1 : Marker? = null

    var marketL2 : Marker? = null
    var foodL2 : Marker? = null
    var bankL2 : Marker? = null

    var activeLevel: Int? = 0

    var focusedBuildingFlag : Boolean = false

    private lateinit var binding: ActivityMapBinding

    fun setUpMap(
        googleMap: GoogleMap,
        //context : Context
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
        map: GoogleMap

    ){
        map.setOnMapLongClickListener { latLng ->


            if (focusedBuildingFlag) {
            //if (true) {

                if(activeLevel == 0) {
                    if (categoryMarker == "market") {
                        if (marketL0 != null) {
                            marketL0?.remove()
                        }
                        marketL0 = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(categoryMarker)
                                .draggable(true)

                        )
                    }
                }
                if(activeLevel == 0) {
                    if (categoryMarker == "restauracja") {
                        if (foodL0 != null) {
                            foodL0?.remove()
                        }
                        foodL0 = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(categoryMarker)
                                .draggable(true)

                        )
                    }
                }
                if(activeLevel == 0) {
                    if (categoryMarker == "bank") {
                        if (bankL0 != null) {
                            bankL0?.remove()
                        }
                        bankL0 = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(categoryMarker)
                                .draggable(true)
                        )
                    }
                }

                if(activeLevel == 1) {
                    if (categoryMarker == "market") {
                        if (marketL1 != null) {
                            marketL1?.remove()
                        }
                        marketL1 = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(categoryMarker)
                                .draggable(true)

                        )
                    }
                }
                if(activeLevel == 1) {
                    if (categoryMarker == "restauracja") {
                        if (foodL1 != null) {
                            foodL1?.remove()
                        }
                        foodL1 = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(categoryMarker)
                                .draggable(true)

                        )
                    }
                }
                if(activeLevel == 1) {
                    if (categoryMarker == "bank") {
                        if (bankL1 != null) {
                            bankL1?.remove()
                        }
                        bankL1 = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(categoryMarker)
                                .draggable(true)

                        )
                    }
                }

                if(activeLevel == 2) {
                    if (categoryMarker == "market") {
                        if (marketL2 != null) {
                            marketL2?.remove()
                        }
                        marketL2 = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(categoryMarker)
                                .draggable(true)

                        )
                    }
                }
                if(activeLevel == 2) {
                    if (categoryMarker == "restauracja") {
                        if (foodL2 != null) {
                            foodL2?.remove()
                        }
                        foodL2 = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(categoryMarker)
                                .draggable(true)

                        )
                    }
                }
                if(activeLevel == 2) {
                    if (categoryMarker == "bank") {
                        if (bankL2 != null) {
                            bankL2?.remove()
                        }
                        bankL2 = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(categoryMarker)
                                .draggable(true)

                        )
                    }
                }

            }


        }
    }


        fun indoor(
            map: GoogleMap,
            context:Context
        ){

            map.setOnIndoorStateChangeListener(object : GoogleMap.OnIndoorStateChangeListener {
                override fun onIndoorBuildingFocused() {
                    context.showToast("onIndoorBuildingFocused")
                    focusedBuildingFlag = !focusedBuildingFlag
//                    if (focusedBuildingFlag) {
//                        binding.floatingBtn.visibility = View.VISIBLE
//                    }
//                    else{
//                        binding.floatingBtn.visibility = View.INVISIBLE
//                    }


                }

                override fun onIndoorLevelActivated(indoorBuilding: IndoorBuilding) {
                    context.showToast("onIndoorLevelActivated")

                    activeLevel = indoorBuilding.activeLevelIndex

                    if(activeLevel == 0){

                        marketL0?.isVisible = true
                        foodL0?.isVisible = true
                        bankL0?.isVisible = true

                        marketL1?.isVisible = false
                        foodL1?.isVisible = false
                        bankL1?.isVisible = false

                        marketL2?.isVisible = false
                        foodL2?.isVisible = false
                        bankL2?.isVisible = false

                    }
                    if (activeLevel == 1) {

                        marketL0?.isVisible = false
                        foodL0?.isVisible = false
                        bankL0?.isVisible = false

                        marketL1?.isVisible = true
                        foodL1?.isVisible = true
                        bankL1?.isVisible = true

                        marketL2?.isVisible = false
                        foodL2?.isVisible = false
                        bankL2?.isVisible = false

                    }
                    if (activeLevel == 2){

                        marketL0?.isVisible = false
                        foodL0?.isVisible = false
                        bankL0?.isVisible = false

                        marketL1?.isVisible = false
                        foodL1?.isVisible = false
                        bankL1?.isVisible = false

                        marketL2?.isVisible = true
                        foodL2?.isVisible = true
                        bankL2?.isVisible = true

                    }

                }
            })
        }

    fun Context.showToast(
        msg: String
    ) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}