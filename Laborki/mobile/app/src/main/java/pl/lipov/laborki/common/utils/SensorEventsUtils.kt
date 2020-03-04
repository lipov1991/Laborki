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
    val onAccelerometerNotDetected = MutableLiveData<String>()

    init
    {
        if (accelerometer == null)
        {
            onAccelerometerNotDetected.postValue("Nie wykryto akcelerometra.")
        }
        else
        {
            sensorManager.registerListener(this, accelerometer, 4000)
        }
    }

    override fun onSensorChanged(
        sensorEvent: SensorEvent
    ) {
        if(sensorEvent.sensor == accelerometer)
        {
            if(sensorEvent.values[0] > 5 && sensorEvent.values[1] > 5)
            {
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
