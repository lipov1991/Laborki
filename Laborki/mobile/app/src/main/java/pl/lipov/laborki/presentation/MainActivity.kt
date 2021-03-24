package pl.lipov.laborki.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMainBinding
import pl.lipov.laborki.presentation.login.LoginCallback

class MainActivity :
    AppCompatActivity(),
    LoginCallback {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mDetector: GestureDetectorCompat

    public override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container,
            LoginFragment()
        )
            .addToBackStack(null)
            .commit()

        mDetector = GestureDetectorCompat(this, viewModel.gestureDetectorUtils)



        viewModel.run {
//            onAccelerometerNotDetected.observe(this@MainActivity) {
//                binding.info.text = getString(R.string.no_accelerometer_detected)
//            }
            onGestureEvent.observe(this@MainActivity) {
                passwordAuthentication.postValue(it)
            }
            onSensorEvent.observe(this@MainActivity) {
                passwordAuthentication.postValue(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.sensorRegistration()
    }

    override fun onPause() {
        super.onPause()
        viewModel.sensorUnregistration()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onLoginSuccess() {
        Toast.makeText(this, "Zalogowano", Toast.LENGTH_SHORT).show()
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        val action: Int = MotionEventCompat.getActionMasked(event)
//
//        return when (action) {
//            MotionEvent.ACTION_DOWN -> {
//                Toast.makeText(this, "ACTION_DOWN", Toast.LENGTH_SHORT).show()
//                true
//            }
//            MotionEvent.ACTION_MOVE -> {
//                Toast.makeText(this, "ACTION_MOVE", Toast.LENGTH_SHORT).show()
//                true
//            }
//
//            else -> super.onTouchEvent(event)
//        }
//    }
}
