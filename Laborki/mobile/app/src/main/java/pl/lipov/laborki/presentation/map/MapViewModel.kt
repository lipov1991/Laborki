package pl.lipov.laborki.presentation.map

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import pl.lipov.laborki.common.utils.MapUtils
import pl.lipov.laborki.common.utils.STTUtils
import pl.lipov.laborki.data.repository.api.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.GalleryDto

class MapViewModel(
    val mapUtils: MapUtils,
    val STTUtils: STTUtils,
    private val loginRepository: LoginRepository

) : ViewModel() {
    var galleries: MutableList<GalleryDto> = mutableListOf()

    fun setUpMap(
        googleMap: GoogleMap,
        markerTitle: String,
        listView: List<View>
    ) {
        mapUtils.setUpMap(googleMap, markerTitle, listView)
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
        Toast.makeText(
            context,
            "Plan zagospodarowania został wysłany na serwer.",
            Toast.LENGTH_LONG
        ).show()
    }

    fun listenForSpeechRecognize(
        activity: AppCompatActivity,
        requestCode: Int
    ) {
        STTUtils.listenForSpeechRecognize(activity, requestCode)
    }

    fun handleSpeechRecognizeResult(
        resultCode: Int,
        data: Intent?,
        context: Context
    ) {
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                STTUtils.handleSpeechRecognizeResult(data, context, mapUtils)
            }
        }
    }

}
