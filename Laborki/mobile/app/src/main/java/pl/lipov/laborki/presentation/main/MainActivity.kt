package pl.lipov.laborki.presentation.main

import android.content.Intent
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
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.ActivityMainBinding
import pl.lipov.laborki.presentation.LoginFirstScreen
import pl.lipov.laborki.presentation.login.LoginCallback
import pl.lipov.laborki.presentation.login.LoginFragment
import pl.lipov.laborki.presentation.map.MapActivity


private const val DEBUG_TAG = "Gestures"

class MainActivity() : AppCompatActivity(), GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener,
    LoginCallback, SensorEventListener
{

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    lateinit var mDetector: GestureDetectorCompat
    private lateinit var sensorManager: SensorManager

    val loginKeys = mutableListOf<String>()
    var counter: Int = 0



    override fun onCreate(
        savedInstanceState: Bundle?
    ) {


        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setUpSensorStuff()
        mDetector = GestureDetectorCompat(this, this)

        mDetector.setOnDoubleTapListener(this)


        binding.testButton.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
            }


        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .replace(R.id.fragment_container, LoginFragment())
            .commit()

        navigateTo(LoginFirstScreen())



//       compositeDisposable.add(
//            viewModel.getUsers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    var message = ""
//                    it.forEach { user ->
//                        message += "${user.name}: ${user.unlockKey.event1}" +
//                                "${user.unlockKey.event2}, ${user.unlockKey.event3}" +
//                                "${user.unlockKey.event4}\n\n"
//                    }
//                }, {  binding.textView.text = message
//                    binding.textView.text = it.LocalizedMessage?: "$it"
//                })
//        )


        //Przygotowanie do integracji z firebase

        //val database = Firebase.database
        //database.getReference("Test-1").setValue("test-2")
        //val myRef = database.getReference("Test-1").setValue("test-2")
        // myRef.setValue("Test-1")

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
        Toast.makeText(this, "Zalogowano", Toast.LENGTH_LONG).show()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onLongPress: $event")
        loginKeys.add(Event.LONG_CLICK.toString())

        counter = counter?.plus(1)
        counter.toString()
        Toast.makeText(this, counter.toString().plus("/4"), Toast.LENGTH_LONG).show()

    }
    override fun onDoubleTap(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTap: $event")
        loginKeys.add(Event.DOUBLE_TAP.toString())
        counter = counter?.plus(1)
        Toast.makeText(this, counter.toString().plus("/4"), Toast.LENGTH_LONG).show()



        return true
    }

    override fun onSensorChanged(event: SensorEvent?)
    {
        if (event != null)
        {
            if (event.values[0] > 5 &&  event.values[1] > 5)
            {
                Toast.makeText(this, "ACCELERATION_CHANGE", Toast.LENGTH_LONG).show()
                loginKeys.add(Event.ACCELERATION_CHANGE.toString())
                counter = counter?.plus(1)
                Toast.makeText(this, counter.toString().plus("/4"), Toast.LENGTH_LONG).show()

            }
        }
        else
        {
            Toast.makeText(this, "Nie wykryto akcelerometra", Toast.LENGTH_LONG).show()
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


     fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().
        setCustomAnimations(R.anim.fade_in,R.anim.fade_out).
        replace(R.id.fragment_container, fragment).
        commit()
    }



}
