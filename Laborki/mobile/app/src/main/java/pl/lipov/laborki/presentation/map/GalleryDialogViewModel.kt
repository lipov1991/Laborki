package pl.lipov.laborki.presentation.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.common.utils.MapUtils


class GalleryDialogViewModel(
        private val mapUtils: MapUtils
) : ViewModel() {

    var actualGallery = mapUtils.actualGallery

    fun setActualGallery(position: Int){
        actualGallery.postValue(position)
    }

}