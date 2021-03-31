package pl.lipov.laborki.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.common.utils.GestureDetectorUtils
import pl.lipov.laborki.common.utils.SensorEventsUtils
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.model.Event

class MainViewModel(
        val gestureDetectorUtils: GestureDetectorUtils,
        private val sensorEventsUtils: SensorEventsUtils,
        private val loginRepository: LoginRepository
) : ViewModel() {

    var auth = MutableLiveData<Event>()
    val onAccelerometerNotDetected: MutableLiveData<Unit> =
            sensorEventsUtils.onAccelerometerNotDetected
    val onGestureEvent: MutableLiveData<Event> = gestureDetectorUtils.onEvent
    val onSensorEvent: MutableLiveData<Event> = sensorEventsUtils.onEvent
}
