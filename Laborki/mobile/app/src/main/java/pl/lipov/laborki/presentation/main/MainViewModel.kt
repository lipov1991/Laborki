package pl.lipov.laborki.presentation.main

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.common.utils.CompassUtils
import pl.lipov.laborki.common.utils.GestureDetectorUtils
import pl.lipov.laborki.common.utils.SensorEventsUtils
import pl.lipov.laborki.common.utils.STTUtils
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.repository.api.LoginRepository

class MainViewModel(
    val gestureDetectorUtils: GestureDetectorUtils,
    private val sensorEventsUtils: SensorEventsUtils,
    private val loginRepository: LoginRepository
) : ViewModel() {

    var passwordAuthentication = MutableLiveData<Event>()
    val onAccelerometerNotDetected: MutableLiveData<Unit> =
        sensorEventsUtils.onAccelerometerNotDetected
    val onGestureEvent: MutableLiveData<Event> = gestureDetectorUtils.onEvent
    val onSensorEvent: MutableLiveData<Event> = sensorEventsUtils.onEvent

    fun sensorRegistration() {
        sensorEventsUtils.listenerRegistration()
    }

    fun sensorUnregistration() {
        sensorEventsUtils.listenerUnregistration()
    }

    fun listenSpeechRecognize() {

    }

    fun handleActivityResult() {

    }

//    fun setUpCompass(
//        context: Context,
//        rotationCallback: (Float) -> Unit
//    ) {
//       sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager?
//
//    }
}
