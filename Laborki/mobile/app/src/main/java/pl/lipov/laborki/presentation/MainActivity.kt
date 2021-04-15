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
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.ActivityMainBinding

//private const val DEBUG_TAG = "Gestures"


class MainActivity :
        AppCompatActivity(),
        LoginCallback,
        SensorEventListener,
        ViewRouter,
        LoginFirstScreenInterface
{

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    private lateinit var mDetector: GestureDetectorCompat

    private lateinit var sensorManager: SensorManager

    val connector: ConnectFragment by viewModels()

    var test = " "

    var OnDoubleTapFlag = ""
    var OnDoubleTapFlag2 = false
    var OnLongPressFlag = ""
    var OnAccChange = false
    var passwordFlag = ""

    fun testowa() {
        test = "DZIALA"
    }

    // fun that changes var state
    fun OnDoubleTapFlagchange() {
        if (passwordFlag.length == 4) {
            passwordFlag = ""
        }
        // to prevent form very long variable
        passwordFlag += "D"
    }

    fun OnLongPressFlagchange() {
        if (passwordFlag.length == 4) {
            passwordFlag = ""
        }
        passwordFlag += "L"
    }

    fun OnAccFlagchange() {
        if (passwordFlag.length == 4) {
            passwordFlag = ""
        }
        passwordFlag += "A"
    }


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportFragmentManager.beginTransaction().
//        setCustomAnimations(R.anim.fade_in,R.anim.fade_out).
//        replace(R.id.fragment_container, LoginFirstScreen()).
//        commit()
        showFragment(LoginFirstScreen())
        mDetector = GestureDetectorCompat(this, MyGestureListener(this, test))
        setUp()

        //val database = Firebase.database
       // val myRef = database.getReference("kas")

       //myRef.setValue("it works!")

//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                dataSnapshot.getValue<String>()?.let {value ->
//                    showToast(value)
//                }
//
//            }

//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//
//            }
       // })
    }

    fun showFragment(
        fragment: Fragment
    ) {
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

    override fun onLoginDBSuccess(){
        Toast.makeText(this@MainActivity, "Login ok!", Toast.LENGTH_LONG).show()
    }
    override fun onLoginDBNoUser(){
        Toast.makeText(this@MainActivity, "Nie ma takiego użytkownika", Toast.LENGTH_LONG).show()

    }
    override fun onLoginDBError(){
        Toast.makeText(this@MainActivity, "Database error", Toast.LENGTH_LONG).show()
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


    private class MyGestureListener(private var activ: MainActivity, var test: String) :
        GestureDetector.SimpleOnGestureListener() {

        var wykryjDoubleTap = test

        override fun onLongPress(event: MotionEvent) {
            Toast.makeText(activ, "LONG_PRESS", Toast.LENGTH_SHORT).show()
            activ.connector.getEvent.postValue(Event.LONG_CLICK)

            println("LONG")

            activ.OnLongPressFlagchange()

            //ttest = "Fragment ok"

        }

        override fun onDoubleTap(event: MotionEvent): Boolean {
            Toast.makeText(activ, "DOUBLE_TAP", Toast.LENGTH_SHORT).show()
            activ.connector.getEvent.postValue(Event.DOUBLE_TAP)

            println("D_TAP")
            activ.OnDoubleTapFlagchange()
            activ.testowa()
            println("OnDoubleTapFlagchange()")
            println(activ.OnDoubleTapFlag)
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


    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (Math.abs(event.values[0]) > 5.0F && Math.abs(event.values[1]) > 5.0F) {
                Toast.makeText(this, "ACCELERATION_CHANGE", Toast.LENGTH_SHORT).show()
                connector.getEvent.postValue(Event.ACCELERATION_CHANGE) // event jest enumeracją

                this.OnAccFlagchange()

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
    fun showToast(
        text: String
    ){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}