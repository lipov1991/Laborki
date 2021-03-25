package pl.lipov.laborki.presentation

import android.view.MotionEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.common.utils.GestureDetectorUtils
import pl.lipov.laborki.common.utils.SensorEventsUtils
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.model.Event

class MainViewModel(
        val gestureDetectorUtils: GestureDetectorUtils,
        val sensorEventsUtils: SensorEventsUtils
) : ViewModel() {

    val onAccelerometerNotDetected: MutableLiveData<Unit> =
            sensorEventsUtils.onAccelerometerNotDetected
    val onGestureEvent: MutableLiveData<Event> = gestureDetectorUtils.onEvent
    val onSensorEvent: MutableLiveData<Event> = sensorEventsUtils.onEvent

    fun registerSensorEventListener(){
        sensorEventsUtils.registerSensorEventListener()
    }

    fun unregisterSensorEventListener(){
        sensorEventsUtils.unregisterSensorEventListener()
    }

    val eventList: MutableLiveData<Event> = MutableLiveData()
}
