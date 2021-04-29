package pl.lipov.laborki.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.repository.LoginRepository
import pl.lipov.laborki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ViewRouter, LoginCallback {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var mDetector: GestureDetectorCompat
    private val loginRepository: LoginRepository? = null

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateTo(UsernameFragment())

//        // Write a message to the database
//        val database = Firebase.database
//        val myRef = database.getReference("Zatorski")
////
////        myRef.setValue("Tekst")
//
//        // Read from the database
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                dataSnapshot.getValue<String>()?.let {
//                    showToast(it)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//            }
//        })

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
                loginRepository?.attemptEnterPassword?.postValue(it)
            }
            onSensorEvent.observe(this@MainActivity) {
                loginRepository?.attemptEnterPassword?.postValue(it)
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

    override fun onUsernameSuccess() {
        Toast.makeText(this@MainActivity, "Poprawna nazwa uzytkownika", Toast.LENGTH_LONG).show()
    }

    override fun onUsernameError() {
        Toast.makeText(this@MainActivity, "Błąd. Nieporawna nazwa uzytkownika", Toast.LENGTH_LONG).show()
    }

    override fun onPasswordSuccess() {
        Toast.makeText(this@MainActivity, "Poprawna sekwencja gestów. Zalogowano do bazy", Toast.LENGTH_LONG).show()
    }

    override fun onPasswordError() {
        Toast.makeText(this@MainActivity, "Błąd. Niepoprawna sekwencja gestów", Toast.LENGTH_LONG).show()
    }

    override fun onAttemptLimit() {
        Toast.makeText(this@MainActivity, "Urządzenie zostało zablokowane z powodu przekroczenia liczby prób", Toast.LENGTH_LONG).show()
    }

}

