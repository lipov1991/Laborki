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
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            val x = sensorEvent.values[0]
            val y = sensorEvent.values[1]
            val z = sensorEvent.values[2]

            if ((x > 5) or (y > 5)){
                Log.d(TAG, " value $x $y.")
                onEvent.postValue(Event.ACCELERATION_CHANGE)
            }
        }

//        else {
//            TODO: implement accelerometer==null
//            onAccelerometerNotDetected.postValue(Unit)
//        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor,
        accuracy: Int
    ) {
        Log.d(TAG, "${sensor.name} accuracy changed to $accuracy.")
    }
}
