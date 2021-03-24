package pl.lipov.laborki.common.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import pl.lipov.laborki.data.model.Event

class SensorEventsUtils(
    private val sensorManager: SensorManager,
    private val accelerometer: Sensor?
) : SensorEventListener {

    companion object {
        private const val TAG = "sensor_events_utils"
    }

    val onEvent = MutableLiveData<Event>()
    val onAccelerometerNotDetected = MutableLiveData<Unit>()

    override fun onSensorChanged(event: SensorEvent?) {
        val x = event?.values!![0]
        val y = event.values!![1]
        val z = event.values!![2]

        if ((x > 5) or (y > 5)) {
            onEvent.postValue(Event.ACCELERATION_CHANGE)
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor,
        accuracy: Int
    ) {
        Log.d(TAG, "${sensor.name} accuracy changed to $accuracy.")
    }

    fun listenerRegistration() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun listenerUnregistration() {
        sensorManager.unregisterListener(this, accelerometer)
    }
}
