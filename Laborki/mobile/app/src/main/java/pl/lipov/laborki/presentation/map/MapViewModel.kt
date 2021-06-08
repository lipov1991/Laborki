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
import org.json.JSONArray
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

    var pin : Marker? = null
    var pin_gallery : Marker? = null


    var activeLevel: Int? = 0

    var focusedBuildingFlag : Boolean = false

    private lateinit var binding: ActivityMapBinding
    private val compositeDisposable = CompositeDisposable()
    lateinit var galleryList : List<Gallery>
    var currentGalleryPosition = mapUtils.currentGalleryPosition
    var googleMap: GoogleMap? = null
    var ifUploadClick = false

    var provider: HeatmapTileProvider? = null
    var tileOverlay: TileOverlay? = null
    var categoryType: String? = "market"


    var galeriaLevelList: List<Level> = listOf(
        Level(0),
        Level(1),
        Level(2)
    )



    fun setUpMap(
        googleMap: GoogleMap,
        context : Context
    )
    {
        this.googleMap = googleMap
        val cameraPosition = CameraPosition.Builder()
            .target(placeCoordinates)
            .zoom(10f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        googleMap.setMinZoomPreference(10f)

       pin_gallery= googleMap.addMarker(
            MarkerOptions()
                .position(placeCoordinates)
                .title("Galeria Wileńska")


        )

        galeriaLevelList.forEach{
            it.setVisibility(false)
        }

    }

    fun setMarker(
        map: GoogleMap

    ){
        map.setOnMapLongClickListener { latLng ->


            if (focusedBuildingFlag) {
                val activeLevel = galeriaLevelList[activeLevel!!]
                var marker = activeLevel.getMarker(categoryMarker)

                marker?.remove()

                marker = googleMap?.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(categoryMarker)
                        .draggable(true) // allows to drag & drop marker
                )

                if (marker != null) {
                    activeLevel.setMarket(marker,categoryMarker)
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


                }

                override fun onIndoorLevelActivated(indoorBuilding: IndoorBuilding) {
                    context.showToast("onIndoorLevelActivated")
                    googleMap?.setOnIndoorStateChangeListener(object :GoogleMap.OnIndoorStateChangeListener{
                        override fun onIndoorBuildingFocused() {
                            focusedBuildingFlag = !focusedBuildingFlag

                            if (!focusedBuildingFlag){
                                if (activeLevel != null){
                                    galeriaLevelList[activeLevel!!].setVisibility(false)

                                }
                            }
                            else{
                                if (activeLevel != null){
                                    galeriaLevelList[activeLevel!!].setCategoryVisibility(categoryType)
                                }
                            }

                        }

                        override fun onIndoorLevelActivated(indoorBuilding: IndoorBuilding?) {
                            activeLevel = indoorBuilding?.activeLevelIndex
                            Log.i("LOG", activeLevel.toString())

                            for(i in galeriaLevelList.indices){
                                if(i == activeLevel) galeriaLevelList[i].setVisibility(true)
                                else galeriaLevelList[i].setVisibility(false)
                            }

                        }

                    })

                    }
                })
        }

    fun Context.showToast(
        msg: String
    ) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


     fun addHeatMap(
        context: Context,
        map: GoogleMap,
        latLng: LatLng,
        overcrowdingLevel: Int
    ) {

        val weightedLocations = WeightedLatLng(latLng, overcrowdingLevel.toDouble())

        // Get the data: latitude/longitude positions of police stations.
        try {

            if(provider == null){
                provider = HeatmapTileProvider.Builder()
                    .weightedData(mutableListOf(weightedLocations))
                    .build()
            }
            else{
                provider!!.setWeightedData(mutableListOf(weightedLocations))
            }
            provider?.setRadius(50)
            provider?.setOpacity(0.5)

            if(tileOverlay == null){
                tileOverlay = map.addTileOverlay(TileOverlayOptions().tileProvider(provider))
            }
            else{
                tileOverlay!!.clearTileCache()
            }


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
        galeriaLevelList.forEach {
            it.clearMarkers()
        }
    }

    fun uploadBtn(context: Context) {
        Toast.makeText(context, "Plan zagospodarowania został wysłany na serwer", Toast.LENGTH_SHORT).show()

        removeAllMarkers()
        categoryMarker = "Market"
        ifUploadClick = true
    }



}