package pl.lipov.laborki.common.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import pl.lipov.laborki.data.model.Event

class SensorEventsUtils : SensorEventListener {

    companion object {
        private const val TAG = "sensor_events_utils"
    }

    val onEvent = MutableLiveData<Event>()
    val onAccelerometerNotDetected = MutableLiveData<Unit>()
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    override fun onSensorChanged(
        sensorEvent: SensorEvent
    ) {
    }

    override fun onAccuracyChanged(
        sensor: Sensor,
        accuracy: Int
    ) {
        Log.d(TAG, "${sensor.name} accuracy changed to $accuracy.")
    }

    fun initAccelerometer(
        context: Context
    ) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer == null) {
            onAccelerometerNotDetected.postValue(Unit)
        }
    }

    fun registerSensorEventListener() {
        accelerometer?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterSensorEventListener() {
        sensorManager?.unregisterListener(this)
    }
}
