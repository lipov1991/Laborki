package pl.lipov.laborki.presentation.map

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import pl.lipov.laborki.R
import pl.lipov.laborki.common.utils.MapUtils
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.model.Gallery
import pl.lipov.laborki.data.model.PoliceStation
import pl.lipov.laborki.databinding.ActivityMapBinding
import java.security.PrivateKey
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

class MapViewModel (
    private val loginRepository: LoginRepository,
    private val mapUtils: MapUtils
) : ViewModel() {


    private val placeCoordinates = LatLng(52.2550, 21.0378) //-24.89262,131.9301376,4.31z
    //private val placeCoordinates = LatLng(-24.89262,131.9301376)
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
    private val compositeDisposable = CompositeDisposable()
    lateinit var galleryList : List<Gallery>
    var currentGalleryPosition = mapUtils.currentGalleryPosition
    var googleMap: GoogleMap? = null



    fun setUpMap(
        googleMap: GoogleMap,
        context : Context
    )
    {
        this.googleMap = googleMap
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

        addHeatMap(context,googleMap)

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


    private fun addHeatMap(context: Context, map: GoogleMap) {

        // Get the data: latitude/longitude positions of police stations.
        try {
            val policeStations = getPoliceStations(context)


            val weightedLatLngs = policeStations.map {policeStation ->
                val latLng = LatLng(policeStation.lat, policeStation.lng)
                WeightedLatLng(latLng, policeStation.weight)

            }

            // Create a heat map tile provider, passing it the latlngs of the police stations.
            val provider = HeatmapTileProvider.Builder()
                .weightedData(weightedLatLngs)
                .build()

            // Add a tile overlay to the map, using the heat map tile provider.
           map.addTileOverlay(TileOverlayOptions().tileProvider(provider))

        } catch (e: JSONException) {
            Toast.makeText(context, "Problem reading list of locations.", Toast.LENGTH_LONG)
                .show()
        }
    }

    @Throws(JSONException::class)
    private fun getPoliceStations( context: Context): List<PoliceStation> {
        val result: MutableList<LatLng?> = ArrayList()
        val inputStream = context.resources.openRawResource(R.raw.police_stations)
        val json = Scanner(inputStream).useDelimiter("\\A").next()
        val itemType = object : TypeToken<List<PoliceStation>>() {}.type

        return Gson().fromJson<List<PoliceStation>>(json, itemType)

    }

    fun getGallery(){

        compositeDisposable.add(
            loginRepository.getGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    galleryList = it



                }, {
                    Log.i("log", "error")
                    //Toast.makeText(this, it.localizedMessage ?: "$it", Toast.LENGTH_LONG).show()
                })
        )

    }
    fun disposableclear(){
        compositeDisposable.clear()
    }

    fun setCameraOnPosition(latLng: LatLng, googleMap: GoogleMap){
        mapUtils.setCameraOnPosition(latLng, googleMap)

    }



    fun removeAllMarkers(){
        marketL0?.remove()
        marketL1?.remove()
        marketL2?.remove()

        bankL0?.remove()
        bankL1?.remove()
        bankL2?.remove()

        foodL0?.remove()
        foodL1?.remove()
        foodL2?.remove()
    }

    fun uploadBtn(context: Context) {
        Toast.makeText(context, "Plan zagospodarowania został wysłany na serwer", Toast.LENGTH_SHORT).show()

        removeAllMarkers()
        categoryMarker = "Market"
    }



}