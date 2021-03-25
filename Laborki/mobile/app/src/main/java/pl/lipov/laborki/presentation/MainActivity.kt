package pl.lipov.laborki.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.MutableLiveData
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoginCallback{

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var sensorManager: SensorManager
    private var mSensor: Sensor? = null

    override fun onCreate(
            savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
                .beginTransaction()     //transakcja przejscia do pierwszego fragmentu
                .replace(R.id.fragment_container, LoginFragment())      //zamiana zawartosci konteneru activity_main.xml na LoginFragment
                .commit()

        mDetector = GestureDetectorCompat(this, viewModel.gestureDetectorUtils)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        viewModel.run {
            onAccelerometerNotDetected.observe(::getLifecycle) {
            }
            onGestureEvent.observe(::getLifecycle){
                viewModel.eventList.postValue(it)
            }
            onSensorEvent.observe(::getLifecycle) {
                viewModel.eventList.postValue(it)
            }
        }
   }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onResume() { //zarejstrować SensorEventListener
        super.onResume()
        viewModel.registerSensorEventListener()
    }

    override fun onPause() { //wyrejestrować SensorEventListener
        super.onPause()
        viewModel.unregisterSensorEventListener()
    }

    override fun onLoginSuccess() {
        Toast.makeText(this, "Udało się zalogować", Toast.LENGTH_LONG).show()
    }

    override fun onLoginUnSuccess() {
        Toast.makeText(this, "Nie udało się zalogować", Toast.LENGTH_LONG).show()
    }

    override fun Block() {
        Toast.makeText(this, "Zablokowano interfejs", Toast.LENGTH_LONG).show()
    }
}