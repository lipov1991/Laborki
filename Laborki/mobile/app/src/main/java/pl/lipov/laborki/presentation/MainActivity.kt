package pl.lipov.laborki.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMainBinding
import androidx.lifecycle.observe
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), ViewRouter {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mDetector: GestureDetectorCompat

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateTo(UsernameFragment())

        mDetector = GestureDetectorCompat(this, viewModel.gestureDetectorUtils)

        viewModel.run {
            onAccelerometerNotDetected.observe(this@MainActivity) {
                Toast.makeText(
                    this@MainActivity,
                    R.string.no_accelerometer_detected,
                    Toast.LENGTH_LONG
                ).show()
            }
            onGestureEvent.observe(this@MainActivity) {
                attemptEnterPassword.postValue(it)
            }
            onSensorEvent.observe(this@MainActivity) {
                attemptEnterPassword.postValue(it)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onResume() {
        super.onResume()
        viewModel.registerSensorEventListener()
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterSensorEventListener()
    }

    override fun navigateTo(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}

