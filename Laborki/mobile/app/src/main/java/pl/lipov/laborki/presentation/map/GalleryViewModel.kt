package pl.lipov.laborki.presentation.map

import androidx.lifecycle.ViewModel
import pl.lipov.laborki.common.utils.MapUtils

class GalleryViewModel(
    private val mapUtils: MapUtils
): ViewModel() {

    val currentGalleryPosition = mapUtils.currentGalleryPosition
}