package pl.lipov.laborki.presentation.map

import android.content.Context
import android.graphics.Point
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.customListAdapter
import com.google.android.gms.maps.GoogleMap
import io.reactivex.Single
import pl.lipov.laborki.R
import pl.lipov.laborki.common.utils.MapUtils
import pl.lipov.laborki.data.repository.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.GalleriesDto

class MapViewModel(
    val mapUtils: MapUtils,
    private val loginRepository: LoginRepository
) : ViewModel() {

    var galleryList: MutableList<GalleriesDto> = mutableListOf()
//    val rotationChange: LiveData<Float> = compassUtils.rotationChange

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
        context: Context
    ) {
        mapUtils.setUpMap(googleMap, context)
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

}