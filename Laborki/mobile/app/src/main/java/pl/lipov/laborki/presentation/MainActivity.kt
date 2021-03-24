package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.core.view.GestureDetectorCompat
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMainBinding
import pl.lipov.laborki.presentation.login.LoginCallback
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity(), SensorEventListener, LoginCallback, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    //    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var mSensorManager:SensorManager
    private lateinit var mAccelerometer :Sensor
    private val screenUnlockKey = listOf<String>("DOUBLE_TAP", "DOUBLE_TAP", "LONG_PRESS", "ACCELERATION_CHANGED")
    private var currentUnlockKey = mutableListOf<String>()
    private var numberOfTries: Int = 3
    private var isLoggedIn: Boolean = false

    private var starAnimator1: ValueAnimator? = null
    private var starAnimator2: ValueAnimator? = null
    private var starAnimator3: ValueAnimator? = null
    private var starAnimator4: ValueAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment()).commit()
        mDetector = GestureDetectorCompat(this, this)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) {
            Toast.makeText(this, "No accelerometer detected!", Toast.LENGTH_SHORT).show()
        }
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

        DrawableCompat.wrap(binding.icStar1.background).mutate()
        DrawableCompat.wrap(binding.icStar2.background).mutate()
        DrawableCompat.wrap(binding.icStar3.background).mutate()
        DrawableCompat.wrap(binding.icStar4.background).mutate()

        starAnimator1 = binding.icStar1.getBackgroundAnimator()
        starAnimator2 = binding.icStar2.getBackgroundAnimator()
        starAnimator3 = binding.icStar3.getBackgroundAnimator()
        starAnimator4 = binding.icStar4.getBackgroundAnimator()

        binding.icBigstar.visibility = View.GONE

        binding.loginButton.setOnClickListener {
            checkLogin()
        }
    }

    private fun View.getBackgroundAnimator(duration: Long = 500, @ColorRes firstColorResId: Int = R.color.grey, @ColorRes secondColorResId: Int = R.color.black): ValueAnimator {
        return ValueAnimator.ofArgb(
                ContextCompat.getColor(context, firstColorResId),
                ContextCompat.getColor(context, secondColorResId)
        ).apply {
            addUpdateListener {
                setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }

    private fun cleanUp() {
        currentUnlockKey.clear()
        val color = ContextCompat.getColor(this, R.color.grey)
        binding.icStar1.background.setTint(color)
        binding.icStar2.background.setTint(color)
        binding.icStar3.background.setTint(color)
        binding.icStar4.background.setTint(color)
    }

    private fun checkLogin() {
        if (numberOfTries != 0) {
            if (currentUnlockKey.size == 4 && currentUnlockKey == screenUnlockKey) {
                onLoginSuccess()
            } else {
                onLoginFail()
            }
        }
    }

    override fun onLoginSuccess() {
        cleanUp()
        numberOfTries = 3
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
        binding.icBigstar.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false
        isLoggedIn = true
    }

    private fun onLoginFail() {
        numberOfTries -= 1
        cleanUp()
        Toast.makeText(this, "Login failed. Attempts left: $numberOfTries", Toast.LENGTH_SHORT).show()
        if (numberOfTries == 0) {
            binding.loginButton.isEnabled = false
            Timer("resetNumberOfTries", true).schedule(5000) {
                numberOfTries = 3
                runOnUiThread() {
                    run() {
                        binding.loginButton.isEnabled = true
                    }
                }
            }
        }
    }

    private fun updateStars() {
        when (currentUnlockKey.size) {
            1 -> starAnimator1?.start()
            2 -> starAnimator2?.start()
            3 -> starAnimator3?.start()
            4 -> starAnimator4?.start()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
//            val sensorName: String = event?.sensor!!.getName();
//            Log.d("Sensor",sensorName + ": X: " + event.values[0] + "; Y: " + event.values[1] + "; Z: " + event.values[2] + ";")
            if (event.values[0] > 5.0 && event.values[1] > 5.0) {
                if (!currentUnlockKey.contains("ACCELERATION_CHANGED") && !isLoggedIn) {
                    currentUnlockKey.add("ACCELERATION_CHANGED")
                    updateStars()
                }
            }
        }
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        if (!isLoggedIn) {
            currentUnlockKey.add("DOUBLE_TAP")
            updateStars()
        }
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        if (!isLoggedIn) {
            currentUnlockKey.add("LONG_PRESS")
            updateStars()
        }
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
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

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return true
    }
}

