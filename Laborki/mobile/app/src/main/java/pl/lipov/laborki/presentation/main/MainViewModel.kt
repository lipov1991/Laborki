package pl.lipov.laborki.presentation.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlay
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import pl.lipov.laborki.R
import pl.lipov.laborki.common.utils.CompassUtils
import pl.lipov.laborki.common.utils.STTUtils
import pl.lipov.laborki.data.model.PoliceStation
import java.util.*

class MainViewModel(
    private val sttUtils: STTUtils,
    private val compassUtils: CompassUtils
) : ViewModel() {

    companion object {
        private const val HEAT_MAP_ANIMATION_LENGTH = 15000L
        private const val HEAT_MAP_ANIMATION_INTERVAL = 1000L
    }

    val rotationChange: LiveData<Float> = compassUtils.rotationChange
    private var heatMapTileProvider: HeatmapTileProvider? = null
    private var tileOverlay: TileOverlay? = null
    private var heatMapVisible = true

    fun setUpMap(
        map: GoogleMap,
        context: Context,
        latLng: LatLng = LatLng(-25.0270548, 115.1824598)
    ) {
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .build()
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        addPoliceStationsHeatMap(map, context)
    }

    private fun addPoliceStationsHeatMap(
        map: GoogleMap,
        context: Context
    ) {
        val locations = getPoliceStations(context)
        val weightedLocations = locations.map { policeStation ->
            val latLng = LatLng(policeStation.lat, policeStation.lng)
            WeightedLatLng(latLng, policeStation.weight)
        }
        heatMapTileProvider = HeatmapTileProvider.Builder()
            .weightedData(weightedLocations)
            .build()
        heatMapTileProvider?.setRadius(50)
        heatMapTileProvider?.let {
            tileOverlay = map.addTileOverlay(TileOverlayOptions().tileProvider(it))
        }
        countDownToStopHeatingAnimation()
    }

    private fun getPoliceStations(
        context: Context,
    ): List<PoliceStation> {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.police_stations)
            val json = Scanner(inputStream).useDelimiter("\\A").next()
            val itemType = object : TypeToken<List<PoliceStation>>() {}.type
            Gson().fromJson<List<PoliceStation>>(json, itemType)
        } catch (exception: Exception) {
            Toast.makeText(context, "Problem reading list of locations.", Toast.LENGTH_LONG)
                .show()
            emptyList()
        }
    }

    private fun countDownToStopHeatingAnimation() {
        object : CountDownTimer(HEAT_MAP_ANIMATION_LENGTH, HEAT_MAP_ANIMATION_INTERVAL) {

            override fun onTick(
                millisUntilFinished: Long
            ) {
                if (heatMapVisible) {
                    setHeatMapOpacity(0.0)
                } else {
                    setHeatMapOpacity(1.0)
                }
                heatMapVisible = !heatMapVisible
            }

            override fun onFinish() {
                setHeatMapOpacity(1.0)
            }
        }.start()
    }

    private fun setHeatMapOpacity(
        opacity: Double
    ) {
        heatMapTileProvider?.setOpacity(opacity)
        tileOverlay?.clearTileCache()
    }

    fun listenForSpeechRecognize(
        activity: AppCompatActivity,
        requestCode: Int
    ) {
        sttUtils.listenForSpeechRecognize(activity, requestCode)
    }

    fun handleSpeechRecognizeResult(
        resultCode: Int,
        data: Intent?,
        context: Context
    ) {
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                sttUtils.handleSpeechRecognizeResult(data, context)
            }
        }
    }

    fun setUpCompass() {
        compassUtils.setUpCompass()
    }
}
