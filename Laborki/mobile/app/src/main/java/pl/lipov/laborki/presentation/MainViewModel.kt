package pl.lipov.laborki.presentation

import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.common.utils.GestureDetectorUtils
import pl.lipov.laborki.common.utils.SensorEventsUtils
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.model.Event

class MainViewModel(
    private val gestureDetectorUtils: GestureDetectorUtils,
    private val sensorEventsUtils: SensorEventsUtils,
    private val loginRepository: LoginRepository
) : ViewModel() {

    val onAccelerometerNotDetected: MutableLiveData<Unit> =
        sensorEventsUtils.onAccelerometerNotDetected
    val onGestureEvent: MutableLiveData<Event> = gestureDetectorUtils.onEvent
    val onSensorEvent: MutableLiveData<Event> = sensorEventsUtils.onEvent

    fun getOnTouchListener(): View.OnTouchListener = gestureDetectorUtils

    fun onTouchEvent(
        event: MotionEvent
    ) {
        gestureDetectorUtils.onTouchEvent(event)
    }

    fun registerSensorEventListener() = sensorEventsUtils.registerSensorEventListener()

    fun unregisterSensorEventListener() = sensorEventsUtils.unregisterSensorEventListener()
}
