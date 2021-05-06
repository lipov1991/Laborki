package pl.lipov.laborki.common.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData

class CompassUtils(
        private val accelerometer: Sensor?,
        private val magnetometer: Sensor?
) {

    val rotationChange = MutableLiveData<Float>()

    private var gravity = FloatArray(3)
    private var geoMagnetic = FloatArray(3)
    private var orientation = FloatArray(3)
    private var rotationMatrix = FloatArray(3)



    private val accelerometerListener: SensorEventListener = object : SensorEventListener{
        override fun onSensorChanged(
                event: SensorEvent
        ) {
            gravity = event.values
            SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geoMagnetic)
            SensorManager.getOrientation(rotationMatrix, orientation)
            ///TODO
            //rotationChange.postValue(a*b/c)
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            TODO("Not yet implemented")
        }
    }

    private val magnetometerListener: SensorEventListener = object : SensorEventListener{
        override fun onSensorChanged(
                event: SensorEvent
        ) {
            geoMagnetic = event.values
            SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geoMagnetic)
            SensorManager.getOrientation(rotationMatrix, orientation)
            ///TODO
            //rotationChange.postValue(a*b/c)
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            TODO("Not yet implemented")
        }
    }

    private fun handleSensorChange(){
        SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geoMagnetic)
        SensorManager.getOrientation(rotationMatrix, orientation)
        //rotationChange.postValue(a*b/c)
    }

    fun setUpCompass(
            context: Context,
            rotationCallback: (Float) -> Unit
    ){

    }

}