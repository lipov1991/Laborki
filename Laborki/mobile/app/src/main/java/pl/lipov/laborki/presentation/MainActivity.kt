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
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.ActivityMainBinding


class MainActivity :
    AppCompatActivity(),
    LoginCallback,
    SensorEventListener

{

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private lateinit var mDetector: GestureDetectorCompat

    private lateinit var sensorManager: SensorManager

    val connector: ConnectFragment by viewModels()

    var test = " "

    var passwordFlag = ""

    fun testowa() {
        test = "DZIALA"
    }

    fun OnDoubleTapFlagchange() {
        if (passwordFlag.length == 4) {
            passwordFlag = ""
        }
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment())
            .commit()

        mDetector = GestureDetectorCompat(this, MyGestureListener(this))
        setUp()

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
            activ.OnLongPressFlagchange()

        }

        override fun onDoubleTap(event: MotionEvent): Boolean {
            Toast.makeText(activ, "DOUBLE_TAP", Toast.LENGTH_SHORT).show()
            activ.connector.getEvent.postValue(Event.DOUBLE_TAP)
            activ.OnDoubleTapFlagchange()
            activ.testowa()
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
        return
    }


    override fun onSensorChanged(event: SensorEvent?) {


        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            val currentX = event.values[0]

            if (currentX.toInt() > 5) {
                //println("ACCif")
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

        }

    }
}