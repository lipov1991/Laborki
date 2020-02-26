package pl.lipov.laborki.common.utils

import android.app.Activity
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import pl.lipov.laborki.data.model.Event

class GestureDetectorUtils : GestureDetector.SimpleOnGestureListener() {

    val onEvent = MutableLiveData<Event>()
    private var gestureDetector: GestureDetector? = null

    fun initGestureDetector(
        activity: Activity
    ) {
        gestureDetector = GestureDetector(activity, this)
    }

    fun onTouchEvent(
        event: MotionEvent
    ) {
        gestureDetector?.onTouchEvent(event)
    }
}
