package pl.lipov.laborki.common.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.activity_main.*
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
    val gravity = arrayOf(0.0,0.0,0.0)
    val linear_acceleration = arrayOf(0.0,0.0,0.0)

    override fun onSensorChanged(
        sensorEvent: SensorEvent
    ) {
        val alpha: Float = 0.8f
        gravity[0] = alpha * gravity[0] + (1 - alpha) * sensorEvent.values[0]
        gravity[1] = alpha * gravity[1] + (1 - alpha) * sensorEvent.values[1]
        gravity[2] = alpha * gravity[2] + (1 - alpha) * sensorEvent.values[2]

        linear_acceleration[0] = sensorEvent.values[0] - gravity[0]
        linear_acceleration[1] = sensorEvent.values[1] - gravity[1]
        linear_acceleration[2] = sensorEvent.values[2] - gravity[2]
        if (linear_acceleration[0] > 5) {
            if (linear_acceleration[1] > 5) {
                println("ACCELERATION_CHANGE")
                onEvent.postValue(Event.ACCELERATION_CHANGE)
            }
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor,
        accuracy: Int
    ) {
        Log.d(TAG, "${sensor.name} accuracy changed to $accuracy.")
    }

}
