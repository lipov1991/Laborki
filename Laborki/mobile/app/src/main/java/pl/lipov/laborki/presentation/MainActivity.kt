package pl.lipov.laborki.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMainBinding
import pl.lipov.laborki.presentation.login.LoginCallback
import pl.lipov.laborki.presentation.login.LoginFragment

class MainActivity :
    AppCompatActivity(),
    LoginCallback {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var sensorClass: MySensorListener

    public override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment())
            .addToBackStack(null)
            .commit()

        mDetector = GestureDetectorCompat(this, MyGestureListener(this))

        sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorClass = MySensorListener(this, sensor)


        //        viewModel.run {
//            onAccelerometerNotDetected.observe(this@MainActivity) {
//                binding.info.text = getString(R.string.no_accelerometer_detected)
//            }
//            onGestureEvent.observe(this@MainActivity) {
//                binding.info.text = it.name
//            }
//            onSensorEvent.observe(this@MainActivity) {
//                binding.info.text = it.name
//            }
//        }
    }

    override fun onResume() {
        sensorManager.registerListener(sensorClass, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        super.onResume()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private class MyGestureListener(val ref: MainActivity) :
        GestureDetector.SimpleOnGestureListener() {

//        override fun onDown(event: MotionEvent): Boolean {
//            Toast.makeText(ref, "down", Toast.LENGTH_SHORT).show()
//            return true
//        }

        override fun onLongPress(e: MotionEvent?) {
            Toast.makeText(ref, "LONG_PRESS", Toast.LENGTH_SHORT).show()
            super.onLongPress(e)
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            Toast.makeText(ref, "DOUBLE_TAP", Toast.LENGTH_SHORT).show()
            return super.onDoubleTap(e)
        }
    }

    private class MySensorListener(val ref: MainActivity, val mySensor: Sensor?) :
        SensorEventListener {

        override fun onAccuracyChanged(
            sensor: Sensor,
            accuracy: Int
        ) {
            Log.d("sensor_events_utils", "${sensor.name} accuracy changed to $accuracy.")
        }

        override fun onSensorChanged(event: SensorEvent?) {
            val x = event?.values!![0]
            val y = event.values!![1]
            val z = event.values!![2]

            if ((x > 5) or (y > 5)) {
                Toast.makeText(ref, "ACCELERATION_CHANGE ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLoginSuccess() {
        Toast.makeText(this, "Zalogowano", Toast.LENGTH_SHORT).show()
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        val action: Int = MotionEventCompat.getActionMasked(event)
//
//        return when (action) {
//            MotionEvent.ACTION_DOWN -> {
//                Toast.makeText(this, "ACTION_DOWN", Toast.LENGTH_SHORT).show()
//                true
//            }
//            MotionEvent.ACTION_MOVE -> {
//                Toast.makeText(this, "ACTION_MOVE", Toast.LENGTH_SHORT).show()
//                true
//            }
//
//            else -> super.onTouchEvent(event)
//        }
//    }
}
