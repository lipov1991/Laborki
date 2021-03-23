package pl.lipov.laborki.common.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import pl.lipov.laborki.data.model.Event

class GestureDetectorUtils: GestureDetector.SimpleOnGestureListener() {

    val onEvent = MutableLiveData<Event>()
    private var gestureDetector: GestureDetector? = null
}
