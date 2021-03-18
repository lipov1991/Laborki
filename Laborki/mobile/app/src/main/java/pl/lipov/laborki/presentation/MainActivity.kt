package pl.lipov.laborki.presentation

import android.content.Context
import android.hardware.*
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.databinding.ActivityMainBinding
import pl.lipov.laborki.presentation.login.LoginCallback

class MainActivity : AppCompatActivity(), LoginCallback, SensorEventListener {


    private val accelerometrThreshold : Float = 5.0F



    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private lateinit var mDetector: GestureDetectorCompat

    private lateinit var accelerometer : Sensor
    private lateinit var sm : SensorManager


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        mDetector = GestureDetectorCompat(this,MyGestureListener(this))

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL)


//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, GestyFragment())
//            .addToBackStack(null)
//            .commit()

//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment())
//            .addToBackStack(null)
//            .commit()

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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

        private class MyGestureListener(acti: AppCompatActivity) :
            GestureDetector.SimpleOnGestureListener() {

            private var mainactiviy : AppCompatActivity = acti

            override fun onDoubleTap(e: MotionEvent?): Boolean {
                Toast.makeText(mainactiviy, "DOUBLE_TAP", Toast.LENGTH_SHORT).show()
                return super.onDoubleTap(e)
            }

            override fun onLongPress(e: MotionEvent?) {
                Toast.makeText(mainactiviy, "LONG_PRESS", Toast.LENGTH_SHORT).show()
                super.onLongPress(e)
            }

        }

    override fun onLoginSuccess() {
        Toast.makeText(this, "Zalogowano", Toast.LENGTH_LONG).show()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event != null){
            if(Math.abs(event.values[0]) > accelerometrThreshold && Math.abs(event.values[1]) > accelerometrThreshold){
                Toast.makeText(this, "ACCELERATION_CHANGE", Toast.LENGTH_SHORT).show()
            }
            else if(event.values[0] == 0.0F && event.values[1] == 0.0F){
                Toast.makeText(this, "Nie wykryto akcelerometra", Toast.LENGTH_SHORT).show()
            }
        }
    }



}
