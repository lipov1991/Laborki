package pl.lipov.laborki.common.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class CompassUtils(
    private val accelerometer: Sensor?,
    private val magnetometer: Sensor?
) {
//    private var gravity = FloatArray(3)
//    private var geoMagnetic = FloatArray(3)
//    private var orientation = FloatArray(3)
//    private var rotationMatrix = FloatArray(9)
//
//    private val accelerometerListener: SensorEventListener = object : SensorEventListener {
//        override fun onSensorChanged(p0: SensorEvent) {
//            gravity = p0.values
//            SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geoMagnetic)
//            SensorManager.getOrientation(rotationMatrix, orientation)
//            rotationChange.postValue()
//            TODO("Not yet implemented")
//        }
//
//        override fun onAccuracyChanged(p0: Sensor, p1: Int) {
//
//            TODO("Not yet implemented")
//        }
//    }
//
//    private val magnetometerListener: SensorEventListener = object : SensorEventListener {
//        override fun onSensorChanged(p0: SensorEvent) {
//            gravity = p0.values
//            SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geoMagnetic)
//            SensorManager.getOrientation(rotationMatrix, orientation)
//            rotationChange.postValue()
//            TODO("Not yet implemented")
//        }
//
//        override fun onAccuracyChanged(p0: Sensor, p1: Int) {
//
//            TODO("Not yet implemented")
//        }
//    }
//
//
//
//    fun setUpCompass(
//        context: Context,
//        rotationCallback: (Float) -> Unit
//    ) {
//
//        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
//
//    }
}