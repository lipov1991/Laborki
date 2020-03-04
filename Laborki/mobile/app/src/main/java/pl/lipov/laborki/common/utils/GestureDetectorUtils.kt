package pl.lipov.laborki.common.utils

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.lifecycle.MutableLiveData
import pl.lipov.laborki.data.model.Event


class GestureDetectorUtils : GestureDetector.SimpleOnGestureListener() {

    val onEvent = MutableLiveData<Event>()

    private var gestureDetector: GestureDetector? = null

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        onEvent.postValue(Event.DOUBLE_TAP)
        super.onDoubleTap(e)
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        onEvent.postValue(Event.LONG_CLICK)
        return super.onLongPress(e)
    }

}
