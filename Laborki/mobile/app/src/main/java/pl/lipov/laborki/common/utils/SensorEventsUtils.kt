package pl.lipov.laborki.common.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
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

    override fun onSensorChanged(
            sensorEvent: SensorEvent
    ) {

        val x = sensorEvent.values[0]
        val y = sensorEvent.values[1]

        if ((x > 5) or (y > 5)) {
            Log.d(TAG, " value $x $y.")
            onEvent.postValue(Event.ACCELERATION_CHANGE)
        }
    }

    override fun onAccuracyChanged(
            sensor: Sensor?,
            accuracy: Int
    ) {
        Log.d(TAG, "${sensor?.name} accuracy changed to $accuracy.")
    }

    fun registerEventListener() {
        if (accelerometer == null) {
            onAccelerometerNotDetected.postValue(Unit)
        } else {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterEventListener() {
        sensorManager.unregisterListener(this, accelerometer)
    }
}
