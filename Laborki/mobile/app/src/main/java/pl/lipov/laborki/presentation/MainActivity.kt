package pl.lipov.laborki.presentation

import android.content.Context
import android.hardware.*
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.common.utils.SensorEventsUtils
import pl.lipov.laborki.databinding.ActivityMainBinding
import pl.lipov.laborki.presentation.login.LoginCallback
import pl.lipov.laborki.presentation.login.LoginFragment


private const val DEBUG_TAG = "Gestures"

class MainActivity() : AppCompatActivity(), GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener, LoginCallback, SensorEventListener
{

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private lateinit var mDetector: GestureDetectorCompat


    private lateinit var sensorManager: SensorManager



    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mDetector = GestureDetectorCompat(this, this)

        mDetector.setOnDoubleTapListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment())
            .addToBackStack(null)
            .commit()

        setUpSensorStuff()

    }

    override fun onLoginSuccess() {
        Toast.makeText(this, "Zalogowano", Toast.LENGTH_LONG).show()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDown: $event")
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onFling: $event1 $event2")
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onLongPress: $event")
        Toast.makeText(this, "Long Press", Toast.LENGTH_LONG).show()

    }

    override fun onScroll(
        event1: MotionEvent,
        event2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onScroll: $event1 $event2")
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onShowPress: $event")

    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapUp: $event")
        return true
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTap: $event")
        Toast.makeText(this, "Double tap", Toast.LENGTH_LONG).show()

        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: $event")
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: $event")
        return true
    }


    private fun setUpSensorStuff()
    {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also{sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)}

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.values[0] > 5 &&  event.values[1] > 5)
            {
                Toast.makeText(this, "ACCELERATION_CHANGE", Toast.LENGTH_LONG).show()
            }
        }
        else {
            Toast.makeText(this, "Nie wykryto akcelerometra", Toast.LENGTH_LONG).show()
        }


    }


}
