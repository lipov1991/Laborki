package pl.lipov.laborki.presentation.map

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import io.reactivex.Single
import pl.lipov.laborki.common.utils.CompassUtils
import pl.lipov.laborki.common.utils.MapUtils
import pl.lipov.laborki.common.utils.STTUtils
import pl.lipov.laborki.data.repository.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.GalleriesDto

class MapViewModel(
    val mapUtils: MapUtils,
    private val loginRepository: LoginRepository,
    val compassUtils: CompassUtils,
    val sttfUtils: STTUtils
) : ViewModel() {

    var galleryList: MutableList<GalleriesDto> = mutableListOf()
    val rotationChange: LiveData<Float> = compassUtils.rotationChange

    fun deleteMarker(
        markerType: String
    ) {
        mapUtils.markersList.removeAll { (it.second == mapUtils.currentIndoorLevel) and (it.first.title == markerType) }
    }

    fun changeMarker(
        markerType: String,
        markerIcon: Int
    ) {
        mapUtils.currentMarkerType = markerType
        mapUtils.currentMarkerIcon = markerIcon
    }

    fun setUpMap(
        googleMap: GoogleMap,
        context: Context,
        listView: List<View>
    ) {
        mapUtils.setUpMap(googleMap, context, listView)
    }

    fun overlap(point: Point, imgview: ImageView): Boolean {
        val imgCoords = IntArray(2)
        imgview.getLocationOnScreen(imgCoords)
        val overlapX =
            point.x < imgCoords[0] + imgview.getWidth() && point.x > imgCoords[0] - imgview.getWidth()
        val overlapY =
            point.y < imgCoords[1] + imgview.getHeight() && point.y > imgCoords[1] - imgview.getWidth()
        return overlapX && overlapY
    }

    fun getGalleries(): Single<List<GalleriesDto>> {
        return loginRepository.getGalleries()
    }

    fun listenForSpeechRecognize(
        activity: AppCompatActivity,
        requestCode: Int
    ) {
        sttfUtils.listenForSpeechRecognize(activity, requestCode)
    }

    fun handleSpeechRecognizeResult(
        resultCode: Int,
        data: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                sttfUtils.handleSpeechRecognizeResult(data, mapUtils)
            }
        }
    }

    fun setUpCompass() {
        compassUtils.setUpCompass()
    }

}