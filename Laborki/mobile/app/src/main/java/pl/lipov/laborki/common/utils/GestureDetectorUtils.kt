package pl.lipov.laborki.common.utils

import android.view.GestureDetector
import androidx.lifecycle.MutableLiveData
import pl.lipov.laborki.data.model.Event

open class GestureDetectorUtils : GestureDetector.SimpleOnGestureListener() {

    val onEvent = MutableLiveData<Event>()
    private var gestureDetector: GestureDetector? = null
}
