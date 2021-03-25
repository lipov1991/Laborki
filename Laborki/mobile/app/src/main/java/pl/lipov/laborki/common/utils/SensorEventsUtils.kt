package pl.lipov.laborki.common.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.icu.util.TimeUnit.values
import android.util.Log
import androidx.lifecycle.MutableLiveData
import pl.lipov.laborki.data.model.Event
import java.time.chrono.JapaneseEra.values

class SensorEventsUtils(
        private val sensorManager: SensorManager, //zarejstrowanie Listerenra za pomocą, sensorManager.registerListener(...
        //sensorManager.unregisterListener(this) wyeretrowanie
        private val accelerometer: Sensor?
) : SensorEventListener {

    companion object {
        private const val TAG = "sensor_events_utils"
    }

    val onEvent = MutableLiveData<Event>() //powiedomienie
    val onAccelerometerNotDetected = MutableLiveData<Unit>()

    override fun onAccuracyChanged(
            sensor: Sensor,
            accuracy: Int
    ) {
        Log.d(TAG, "${sensor.name} accuracy changed to $accuracy.")
    }

    override fun onSensorChanged(// tu robimy if
        sensorEvent: SensorEvent
    ) {
        val x = sensorEvent.values[0]
        val y = sensorEvent.values[1]

        if (x > 5 || y > 5){
            onEvent.postValue(Event.ACCELERATION_CHANGE)
        }
    }

    fun registerSensorEventListener(){
        if(accelerometer==null){
            onAccelerometerNotDetected.postValue(Unit) //w aktywności kod -> wyświetli sie tekst nie wykryto accelerometu.
        }else{
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregisterSensorEventListener(){
        sensorManager.unregisterListener(this, accelerometer)
    }
}
