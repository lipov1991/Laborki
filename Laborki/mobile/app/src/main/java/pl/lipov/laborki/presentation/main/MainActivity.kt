package pl.lipov.laborki.presentation.main

import android.content.Context
import android.content.Intent
import android.hardware.*
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.ActivityMainBinding
import pl.lipov.laborki.presentation.ConnectViewModel
import pl.lipov.laborki.presentation.MainViewModel
import pl.lipov.laborki.presentation.login.LoginCallback
import pl.lipov.laborki.presentation.login.LoginFirstScreenFragment
import pl.lipov.laborki.presentation.map.MapActivity

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
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }


        mDetector = GestureDetectorCompat(this, MyGestureListener(this))

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)



        showFragment(LoginFirstScreenFragment())
    
        //val database = Firebase.database
//        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, GestyFragment())
//            .addToBackStack(null)
//            .commit()

//        supportFragmentManager
//            .beginTransaction()
//            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//            .replace(R.id.fragment_container, LoginFragment())
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

    override fun onResume() {
        super.onResume()
        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sm.unregisterListener(this,accelerometer)
    }

    fun showFragment(
            fragment: Fragment
    ) {
        supportFragmentManager.beginTransaction().run {
            setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.fade_out)
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

        private class MyGestureListener(acti: MainActivity) :
            GestureDetector.SimpleOnGestureListener() {

            private var mainactiviy : MainActivity = acti

            override fun onDoubleTap(e: MotionEvent?): Boolean {
                Toast.makeText(mainactiviy, "DOUBLE_TAP", Toast.LENGTH_SHORT).show()
                mainactiviy.viewModel.getEvent.postValue(Event.DOUBLE_TAP)
                return super.onDoubleTap(e)
            }

            override fun onLongPress(e: MotionEvent?) {
                Toast.makeText(mainactiviy, "LONG_PRESS", Toast.LENGTH_SHORT).show()
                mainactiviy.viewModel.getEvent.postValue(Event.LONG_CLICK)
                super.onLongPress(e)
            }

        }

    override fun onLoginSuccess() {
        Toast.makeText(this, "Zalogowano", Toast.LENGTH_LONG).show()
    }

    override fun onUnsuccess() {
        Toast.makeText(this, "Nie zalogowano sie", Toast.LENGTH_LONG).show()
    }

    override fun blocked() {
        Toast.makeText(this, "Zablokowano", Toast.LENGTH_LONG).show()
    }

    override fun uncorrectUser() {
        Toast.makeText(this, "Nieprawidlowy uzytkownik", Toast.LENGTH_LONG).show()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event != null){
            if(Math.abs(event.values[0]) > accelerometrThreshold && Math.abs(event.values[1]) > accelerometrThreshold){
                Toast.makeText(this, "ACCELERATION_CHANGE", Toast.LENGTH_SHORT).show()
                viewModel.getEvent.postValue(Event.ACCELERATION_CHANGE)
                println("Działa")
            }
            else if(event.values[0] == 0.0F && event.values[1] == 0.0F){
                Toast.makeText(this, "Nie wykryto akcelerometra", Toast.LENGTH_SHORT).show()
                println("Nie działa")
            }
        }
    }



}
