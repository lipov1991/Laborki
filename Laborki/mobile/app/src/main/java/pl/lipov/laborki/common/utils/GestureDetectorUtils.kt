package pl.lipov.laborki.common.utils

import android.app.Activity
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.lifecycle.MutableLiveData
import pl.lipov.laborki.data.model.Event

class GestureDetectorUtils : GestureDetector.SimpleOnGestureListener() {

    val onEvent = MutableLiveData<Event>()
    private var gestureDetector: GestureDetector? = null

    fun initGestureDetector(activity: Activity)
    {
        gestureDetector = GestureDetector(activity, this)
    }

    fun onTouchEvent(motionEvent: MotionEvent) = gestureDetector?.onTouchEvent(motionEvent)

    override fun onLongPress(motionEvent: MotionEvent)
    {
        onEvent.postValue(Event.LONG_CLICK)
        super.onLongPress(motionEvent)
    }

    override fun onDoubleTap(motionEvent: MotionEvent) : Boolean
    {
        onEvent.postValue(Event.DOUBLE_TAP)
        return super.onDoubleTap(motionEvent)
    }
}
