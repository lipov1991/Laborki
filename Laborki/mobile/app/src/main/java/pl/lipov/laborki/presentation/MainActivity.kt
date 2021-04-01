package pl.lipov.laborki.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.ViewRouter
import pl.lipov.laborki.databinding.ActivityMainBinding
import pl.lipov.laborki.presentation.login.LoginCallback

class MainActivity :
    AppCompatActivity(),
    LoginCallback,
    ViewRouter {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mDetector: GestureDetectorCompat

    public override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        val database = Firebase.database
        val myRef = database.getReference("users2")

        myRef.setValue("ff")

        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateTo(TextFragment())
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

    override fun navigateTo(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            .replace(
                R.id.fragment_container,
                fragment
            )
            .addToBackStack(null)
            .commit()
    }

}
