package pl.lipov.laborki.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMainBinding
import pl.lipov.laborki.presentation.login.AuthCallback
import pl.lipov.laborki.presentation.login.AuthFragment
import pl.lipov.laborki.presentation.login.LoginCallback
import pl.lipov.laborki.presentation.login.LoginFragment
import pl.lipov.laborki.presentation.map.MapActivity

class MainActivity : AppCompatActivity(), LoginCallback, AuthCallback {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.fragment_authentication, AuthFragment()).commit()
        mDetector = GestureDetectorCompat(this, viewModel.gestureDetectorUtils)

        viewModel.run {
            onGestureEvent.observe(this@MainActivity) {
                auth.postValue(it)
            }
            onSensorEvent.observe(this@MainActivity) {
                auth.postValue(it)
            }
        }
    }

    override fun showMap() {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onLoginSuccess() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_authentication, LoginFragment()).commit()
    }
}