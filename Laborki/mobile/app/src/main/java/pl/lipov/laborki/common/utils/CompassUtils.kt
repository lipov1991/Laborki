package pl.lipov.laborki.common.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData

class CompassUtils(
    private val accelerometer: Sensor?,
    private val magnetometer: Sensor?,
    private val sensorManager: SensorManager
) {
    val rotationChange = MutableLiveData<Float>()
    private var gravity = FloatArray(3)
    private var geoMagnetic = FloatArray(3)
    private var orientation = FloatArray(3)
    private var rotationMatrix = FloatArray(9)

    private val accelerometerListener: SensorEventListener = object : SensorEventListener {

        override fun onSensorChanged(
            event: SensorEvent
        ) {
            gravity = event.values
            handleSensorChange()
        }

        override fun onAccuracyChanged(
            sensor: Sensor,
            accuracy: Int
        ) {
//            TODO("Not yet implemented")
        }
    }

    private fun handleSensorChange() {
        SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geoMagnetic)
        SensorManager.getOrientation(rotationMatrix, orientation)
        rotationChange.postValue( (-orientation[0]*180/3.14).toFloat() )
    }

    private val magnetometerListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(
            event: SensorEvent
        ) {
            geoMagnetic = event.values
            handleSensorChange()
        }

        override fun onAccuracyChanged(
            sensor: Sensor,
            accuracy: Int
        ) {
//           TODO("Not yet implemented")
        }
    }

    fun setUpCompass() {
        sensorManager.registerListener(
            accelerometerListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager.registerListener(
            magnetometerListener,
            magnetometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

}