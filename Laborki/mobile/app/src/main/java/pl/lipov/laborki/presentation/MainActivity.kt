package pl.lipov.laborki.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

//class MainActivity : AppCompatActivity(), LoginCallback {
class MainActivity : AppCompatActivity(){

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

        mDetector = GestureDetectorCompat(this, viewModel.gestureDetectorUtils)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        suspend fun delay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) {}


        //       viewModel.loginResult.observe(::getLifecycle) { result->
        //           Toast.makeText(this, result, Toast.LENGTH_LONG).show()
//        } //obsługa rezultatu

//        binding.loginButton.setOnClickListener {
//            viewModel.signIn("Pati", password = "abc")
//        } //podpięcie akcji,wywołoanie funkcji do logowania w odpowiedzi do przyciśnięcia przycisku

        viewModel.run {
            onAccelerometerNotDetected.observe(::getLifecycle) {
                binding.info.text = getString(R.string.no_accelerometer_detected)
            }
            runBlocking {
                delay(10, TimeUnit.SECONDS)
                onGestureEvent.observe(::getLifecycle){
                    binding.info.text = it.name
                }
            }
            onSensorEvent.observe(::getLifecycle) {
                binding.info.text = it.name
            }
        }
    }

//    override fun onLoginSuccess() {
//        Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show()
//    }

//    override fun onTouchEvent(
//        event: MotionEvent?
//    ): Boolean {
//        //event?.action
    //       return super.onTouchEvent(event)
    //   }//możemy ją nadpisać

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    //funkcje do accelerometera
    override fun onResume() { //zarejstrować SensorEventListener
        super.onResume()
        viewModel.registerSensorEventListener()
    }

    override fun onPause() { //wyrejestrować SensorEventListener
        super.onPause()
        viewModel.unregisterSensorEventListener()
    }
}


