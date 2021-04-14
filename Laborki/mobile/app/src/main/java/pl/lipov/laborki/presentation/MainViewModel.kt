package pl.lipov.laborki.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.common.utils.GestureDetectorUtils
import pl.lipov.laborki.common.utils.SensorEventsUtils
import pl.lipov.laborki.data.repository.LoginRepository
import pl.lipov.laborki.data.model.Event

class MainViewModel(
        val gestureDetectorUtils: GestureDetectorUtils,
        private val sensorEventsUtils: SensorEventsUtils,
        val loginRepository: LoginRepository
) : ViewModel() {

    val onAccelerometerNotDetected: MutableLiveData<Unit> =
            sensorEventsUtils.onAccelerometerNotDetected
    val onGestureEvent: MutableLiveData<Event> = gestureDetectorUtils.onEvent
    val onSensorEvent: MutableLiveData<Event> = sensorEventsUtils.onEvent
    val onEvent: MutableLiveData<Event> = loginRepository.attemptEnterPassword

    fun registerSensorEventListener() {
        sensorEventsUtils.registerEventListener()
    }

    fun unregisterSensorEventListener() {
        sensorEventsUtils.unregisterEventListener()
    }

//    fun getLogin() {
//        loginRepository.getLogin()
//    }

}
