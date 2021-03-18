package pl.lipov.laborki.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoginCallback {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mDetector: GestureDetectorCompat

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDetector = GestureDetectorCompat(this, viewModel.gestureDetectorUtils)


//        supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.fragment_container, LoginFragment())// or .add
//                //.addToBackStack(null)  - optional add to back stack
//                .commit()

        viewModel.run {
            onAccelerometerNotDetected.observe(this@MainActivity) {
                binding.info.text = getString(R.string.no_accelerometer_detected)
            }
            onGestureEvent.observe(this@MainActivity) {
                binding.info.text = it.name
            }
            onSensorEvent.observe(this@MainActivity) {
                binding.info.text = it.name
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.registerSensorEventListener()
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterSensorEventListener()
    }

    override fun onLoginSuccess() {
        Toast.makeText(this, "Witaj", Toast.LENGTH_LONG).show()
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}
