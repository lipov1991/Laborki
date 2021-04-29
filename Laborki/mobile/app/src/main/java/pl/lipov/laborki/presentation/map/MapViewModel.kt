package pl.lipov.laborki.presentation.map

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import org.json.JSONArray
import org.json.JSONException
import pl.lipov.laborki.R
import pl.lipov.laborki.common.utils.MapUtils
import pl.lipov.laborki.data.model.PoliceStation
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

class MapViewModel(
    private val mapUtils: MapUtils

) : ViewModel() {

    var markercategory: MarkerCategory? = null
    var focusedMarker: Marker? = null
    var focusedBuilding = false
    var activeLevelBuilding:Int? = 0
    var galeriaLevelList: List<IndoorLevel> = listOf(
        IndoorLevel(0),
        IndoorLevel(1),
        IndoorLevel(2)
    )

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


    fun setUpIndoor(
            googleMap: GoogleMap
    ){

        googleMap.setOnIndoorStateChangeListener(object :GoogleMap.OnIndoorStateChangeListener{
            override fun onIndoorBuildingFocused() {
                focusedBuilding = !focusedBuilding

                if (!focusedBuilding){
                    if (activeLevelBuilding != null){
                        galeriaLevelList[activeLevelBuilding!!].setVisibility(false)
                    }
                }
                else{
                    if (activeLevelBuilding != null){
                        galeriaLevelList[activeLevelBuilding!!].setVisibility(true)
                    }
                }

            }

            override fun onIndoorLevelActivated(indoorBuilding: IndoorBuilding?) {
                activeLevelBuilding = indoorBuilding?.activeLevelIndex
                Log.i("LOG", activeLevelBuilding.toString())

                for(i in galeriaLevelList.indices){
                    if(i == activeLevelBuilding) galeriaLevelList[i].setVisibility(true)
                    else galeriaLevelList[i].setVisibility(false)
                }

            }

        })
    }

    fun addHeatMap(
        map:GoogleMap,
        context: Context
    ) {
        var policeStations: List<PoliceStation>? = null

        // Get the data: latitude/longitude positions of police stations.
        try {
            policeStations = getPoliceStations(context)
            val weightedLocations = policeStations.map {
                val latlng =LatLng(it.lat,it.lng)
                WeightedLatLng(latlng,it.weight)
            }

            // Create a heat map tile provider, passing it the latlngs of the police stations.
            val provider = HeatmapTileProvider.Builder()
                .weightedData(weightedLocations)
                .build()

            provider.setRadius(50)

            // Add a tile overlay to the map, using the heat map tile provider.
            map.addTileOverlay(TileOverlayOptions().tileProvider(provider))

        } catch (e: JSONException) {
            Toast.makeText(context, "Problem reading list of locations.", Toast.LENGTH_LONG)
                .show()
        }

    }

    @Throws(JSONException::class)
    private fun getPoliceStations(
        context: Context
    ): List<PoliceStation> {
        val inputStream = context.resources.openRawResource(R.raw.police_stations)
        val json = Scanner(inputStream).useDelimiter("\\A").next()
        val itemType = object : TypeToken<List<PoliceStation>>() {}.type
        return Gson().fromJson<List<PoliceStation>>(json, itemType)
    }


    fun addMarket(
        googleMap: GoogleMap
    ){
        googleMap.setOnMapLongClickListener { latLng ->


        if(focusedBuilding){
            if(activeLevelBuilding != null){

                val activeLevel = galeriaLevelList[activeLevelBuilding!!]
                var marker = activeLevel.getMarker(markercategory)

                marker?.remove()

                marker = googleMap.addMarker(
                               MarkerOptions()
                                       .position(latLng)
                                       .title(markercategory.toString())
                                       .draggable(true) // allows to drag & drop marker
                       )

                activeLevel.setMarket(marker,markercategory)

            }
        }

        }
    }

}