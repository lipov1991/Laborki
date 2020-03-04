package pl.lipov.laborki.presentation

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.common.utils.GestureDetectorUtils
import android.content.Context
import pl.lipov.laborki.common.utils.SensorEventsUtils
import pl.lipov.laborki.data.model.Event


class MainActivity : AppCompatActivity() {

    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var mSensorManager:SensorManager
    private var mSensor: Sensor ?= null
    private val viewModel: MainViewModel by viewModel()
    private lateinit var utilis:GestureDetectorUtils
    private lateinit var accelerate:SensorEventsUtils
    override fun onCreate(
            savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        utilis = GestureDetectorUtils()
        mDetector = GestureDetectorCompat(this,  utilis)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null)
            info_text.text = "Nie wykryto akcelerometra"
        else {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }
        accelerate = SensorEventsUtils(mSensorManager, mSensor)
        setContentView(R.layout.activity_main)
        viewModel.run {
            onAccelerometerNotDetected.observe(::getLifecycle) {
                info_text.text = getString(R.string.no_accelerometer_detected)
            }
            onGestureEvent.observe(::getLifecycle) {
                info_text.text = it.name
            }
            onSensorEvent.observe(::getLifecycle) {
                info_text.text = it.name
            }
        }
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDetector.onTouchEvent(event)
        if(utilis.onEvent.value.toString() != "null") {
            println(utilis.onEvent.value.toString())
            info_text.text = utilis.onEvent.value.toString()
            utilis.onEvent.value = null
        }
        else
            info_text.text = null
        return super.onTouchEvent(event)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(accelerate, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
        return super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(accelerate)
        return super.onPause()
    }

}
