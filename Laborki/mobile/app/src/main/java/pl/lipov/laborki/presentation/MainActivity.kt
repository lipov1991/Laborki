package pl.lipov.laborki.presentation

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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.ActivityMainBinding



class MainActivity :
        AppCompatActivity(),
        LoginCallback,
        SensorEventListener,
        ViewRouter
        //GestureDetector.SimpleOnGestureListener,
        //GestureDetector.OnDoubleTapListener
{

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private lateinit var mDetector: GestureDetectorCompat

    private lateinit var sensorManager: SensorManager

    val connector: ConnectFragment by viewModels()


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showFragment(LoginFirstScreen())
        mDetector = GestureDetectorCompat(this, MyGestureListener(this))
        setUp()

    }

    fun showFragment(
        fragment: Fragment
    ) {
        // animacja przejscia
        supportFragmentManager.beginTransaction().
        setCustomAnimations(R.anim.fade_in,R.anim.fade_out).
        replace(R.id.fragment_container, fragment).
        commit()

    }

    override fun onLoginSuccess() {

        Toast.makeText(this@MainActivity, "Zalogowano pomyślnie!", Toast.LENGTH_LONG).show()
    }
    override fun onLoginFail() {
        Toast.makeText(this@MainActivity, "Błędne hasło!", Toast.LENGTH_LONG).show()
    }
    override fun onLoginLock() {
        Toast.makeText(this@MainActivity, "Zablokowano!", Toast.LENGTH_LONG).show()
    }

//        viewModel.run {
//            onAccelerometerNotDetected.observe(::getLifecycle) {
//                binding.info.text = getString(R.string.no_accelerometer_detected)
//            }
//            onGestureEvent.observe(::getLifecycle) {
//                binding.info.text = it.name
//            }
//            onSensorEvent.observe(::getLifecycle) {
//                binding.info.text = it.name
//            }
//        }
//    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }


    private class MyGestureListener(private var activ: MainActivity) :
        GestureDetector.SimpleOnGestureListener() {


        override fun onLongPress(event: MotionEvent) {
            Toast.makeText(activ, "LONG_PRESS", Toast.LENGTH_SHORT).show()
            activ.connector.getEvent.postValue(Event.LONG_CLICK)

            println("LONG")

        }

        override fun onDoubleTap(event: MotionEvent): Boolean {
            Toast.makeText(activ, "DOUBLE_TAP", Toast.LENGTH_SHORT).show()
            activ.connector.getEvent.postValue(Event.DOUBLE_TAP)

            println("D_TAP")
            println("OnDoubleTapFlagchange()")

            return true
        }

    }

    private fun setUp() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this, it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return // we do not use this
    }

    //    override fun onSensorChanged(event: SensorEvent?) {
//
//        //Log.d("SENSORS", "onSensorChanged: The values are }")
//
//        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
//
//            val currentX = event.values[0]
//            //val currentY = event.values[1]
//            //val currentZ = event.values[2]
//
//
//            if (currentX.toInt() > 5  ) {
//                //println("ACCif")
//                Toast.makeText(this, "ACCELERATION_CHANGE", Toast.LENGTH_SHORT).show()
//                this.OnAccFlagchange()
//            }
//                else {
//                    //Toast.makeText(this, "Nie wykryto akcelerometra.", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (Math.abs(event.values[0]) > 5.0F && Math.abs(event.values[1]) > 5.0F) {
                Toast.makeText(this, "ACCELERATION_CHANGE", Toast.LENGTH_SHORT).show()
                connector.getEvent.postValue(Event.ACCELERATION_CHANGE) // event jest enumeracją



            } else if (event.values[0] == 0.0F && event.values[1] == 0.0F) {
                Toast.makeText(this, "Nie wykryto akcelerometra", Toast.LENGTH_SHORT).show()
            }
        }


    }


    override fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().
        setCustomAnimations(R.anim.fade_in,R.anim.fade_out).
        replace(R.id.fragment_container, fragment).
        commit()
    }
}