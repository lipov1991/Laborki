package pl.lipov.laborki.presentation.map

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import pl.lipov.laborki.common.utils.MapUtils
import pl.lipov.laborki.data.repository.api.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.GalleryDto

class MapViewModel(
    val mapUtils: MapUtils,
    private val loginRepository: LoginRepository

) : ViewModel() {
    var galleries: MutableList<GalleryDto> = mutableListOf()

    fun setUpMap(
        googleMap: GoogleMap,
        markerTitle: String
    ) {
        mapUtils.setUpMap(googleMap, markerTitle)
    }

    fun addMarker(
        googleMap: GoogleMap,
        markerTitle: String,
        coord: LatLng
    ) {
        mapUtils.addMarker(googleMap, markerTitle, coord)
    }

    fun changeMarker(
        markerType: String,
    ) {
        mapUtils.currentMarkerType = markerType
    }

    fun checkMarker(): Boolean {
        if (mapUtils.pinsList.none { (it.second == mapUtils.currentLevel) and (it.first.title == mapUtils.currentMarkerType) }
        ) {
            return true
        }
        return false
    }

    fun getGalleries(): Single<List<GalleryDto>> {
        return loginRepository.getGalleries()
    }

    fun sendingDevelopmentPlan(
        context: Context,
        googleMap: GoogleMap
    ) {
        mapUtils.pinsList.clear()
        googleMap.clear()
        mapUtils.currentMarkerType = "Sklep"
        mapUtils.areaButttonAlreadyPressed = true
        Toast.makeText(context, "Plan zagospodarowania został wysłany na serwer.", Toast.LENGTH_LONG).show()
    }
//    fun addHeatMap(
//        map:GoogleMap,
//        context: Context
//    ) {
//        var policeStations: List<PoliceStation>? = null
//
//        // Get the data: latitude/longitude positions of police stations.
//        try {
//            policeStations = getPoliceStations(context)
//            var weightedLocations = policeStations.map {
//                WeightedLatLng(it.lat, it.lon, it.weight)
//            }
//        } catch (e: JSONException) {
//            Toast.makeText(context, "Problem reading list of locations.", Toast.LENGTH_LONG)
//                .show()
//        }
//
//        // Create a heat map tile provider, passing it the latlngs of the police stations.
//        val provider = HeatmapTileProvider.Builder()
//            .data(policeStations)
//            .build()
//
//        // Add a tile overlay to the map, using the heat map tile provider.
//        val overlay = map.addTileOverlay(TileOverlayOptions().tileProvider(provider))
//    }
//
//    @Throws(JSONException::class)
//    private fun getPoliceStations(
//        context: Context
//    ): List<PoliceStation>? {
//        val result: MutableList<LatLng?> = ArrayList()
//        val inputStream = context.resources.openRawResource(R.raw.police_stations)
//        val json = Scanner(inputStream).useDelimiter("\\A").next()
//
//        val itemType = object : TypeToken<List<PoliceStation>>() {}.type
//        return  Gson().fromJson<List<PoliceStation>>(json, itemType)
//    }

}
