package pl.lipov.laborki.presentation.map

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import org.json.JSONException
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.PoliceStation
import java.util.*

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

     fun addHeatMap(
        map:GoogleMap,
        context: Context
    ) {
        var policeStations: List<PoliceStation>? = null

        // Get the data: latitude/longitude positions of police stations.
        try {
            policeStations = getPoliceStations(context)
            val weightedLocations = policeStations.map {
                val latlng = LatLng(it.lat, it.lng)
                WeightedLatLng(latlng, it.weight)
            }

            val provider = HeatmapTileProvider.Builder()
                .weightedData(weightedLocations)
                .build()

            provider.setRadius(50)
            map.addTileOverlay(TileOverlayOptions().tileProvider(provider))


        } catch (e: JSONException) {
            Toast.makeText(context, "Problem reading list of locations.", Toast.LENGTH_LONG)
                .show()
        }

        // Create a heat map tile provider, passing it the latlngs of the police stations.


        // Add a tile overlay to the map, using the heat map tile provider.
    }

    //@Throws(JSONException::class)
    private fun getPoliceStations(
        context: Context):
            List<PoliceStation> {

        val inputStream = context.resources.openRawResource(R.raw.police_stations)
        val json = Scanner(inputStream).useDelimiter("\\A").next()
        val itemType = object : TypeToken<List<PoliceStation>>() {}.type
        return Gson().fromJson<List<PoliceStation>>(json, itemType)

    }


}

