package pl.lipov.laborki.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
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
import android.util.Log

class MainActivity : AppCompatActivity(), LoginCallback, SensorEventListener {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment())
            .commit()

        mDetector = GestureDetectorCompat(this, MyGestureListener(this))
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) {
            Toast.makeText(this, "Nie wykryto akcelerometra", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLoginSuccess() {
        Toast.makeText(this, "Zalogowano", Toast.LENGTH_LONG).show()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val sensorName: String = event?.sensor!!.getName();
            Log.d("Sensor",sensorName + ": X: " + event.values[0] + "; Y: " + event.values[1] + "; Z: " + event.values[2] + ";")
            if (event.values[0] > 5.0 && event.values[1] > 5.0) {
                Toast.makeText(this, "Zmiana przyspieszenia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    private class MyGestureListener(private var activ: AppCompatActivity) :
        GestureDetector.SimpleOnGestureListener() {

        override fun onLongPress(event: MotionEvent) {
            Toast.makeText(activ, "Long press", Toast.LENGTH_LONG).show()
            super.onLongPress(event)
        }

        override fun onDoubleTap(event: MotionEvent): Boolean {
            Toast.makeText(activ, "Double tap", Toast.LENGTH_SHORT).show()
            return true
        }

    }
}

