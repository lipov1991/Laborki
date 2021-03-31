package pl.lipov.laborki.presentation.login

import android.animation.ValueAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.FragmentLoginBinding
import pl.lipov.laborki.presentation.main.MainViewModel
import java.util.*
import kotlin.concurrent.schedule

class LoginFragment() : Fragment(), SensorEventListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding
    private var loginCallback: LoginCallback? = null
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccelerometer: Sensor
    private val screenUnlockKey = mutableListOf<Event>()
    private var currentUnlockKey = mutableListOf<Event>()
    private var numberOfTries: Int = 3
    private var isLoggedIn: Boolean = false

    private var starAnimator1: ValueAnimator? = null
    private var starAnimator2: ValueAnimator? = null
    private var starAnimator3: ValueAnimator? = null
    private var starAnimator4: ValueAnimator? = null

    val database = Firebase.database
    val dbRef = database.reference

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginCallback) {
            loginCallback = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null) {
            Toast.makeText(context, "No accelerometer detected!", Toast.LENGTH_SHORT).show()
        }

        DrawableCompat.wrap(binding.icStar1.background).mutate()
        DrawableCompat.wrap(binding.icStar2.background).mutate()
        DrawableCompat.wrap(binding.icStar3.background).mutate()
        DrawableCompat.wrap(binding.icStar4.background).mutate()

        starAnimator1 = binding.icStar1.getBackgroundAnimator()
        starAnimator2 = binding.icStar2.getBackgroundAnimator()
        starAnimator3 = binding.icStar3.getBackgroundAnimator()
        starAnimator4 = binding.icStar4.getBackgroundAnimator()

        binding.icBigstar.visibility = View.GONE

        binding.loginButton.setOnClickListener {
            checkLogin()
        }

        viewModel.auth.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            currentUnlockKey.add(it)
            updateStars()
        })

        dbRef.child(viewModel.login).child("event_1").get().addOnSuccessListener {
            convToEvent(it.value as String)?.let { it1 -> screenUnlockKey.add(it1) }
        }
        dbRef.child(viewModel.login).child("event_2").get().addOnSuccessListener {
            convToEvent(it.value as String)?.let { it1 -> screenUnlockKey.add(it1) }
        }
        dbRef.child(viewModel.login).child("event_3").get().addOnSuccessListener {
            convToEvent(it.value as String)?.let { it1 -> screenUnlockKey.add(it1) }
        }
        dbRef.child(viewModel.login).child("event_4").get().addOnSuccessListener {
            convToEvent(it.value as String)?.let { it1 -> screenUnlockKey.add(it1) }
        }
    }

    private fun View.getBackgroundAnimator(duration: Long = 500, @ColorRes firstColorResId: Int = R.color.grey, @ColorRes secondColorResId: Int = R.color.black): ValueAnimator {
        return ValueAnimator.ofArgb(
                ContextCompat.getColor(context, firstColorResId),
                ContextCompat.getColor(context, secondColorResId)
        ).apply {
            addUpdateListener {
                DrawableCompat.setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }

    private fun cleanUp() {
        currentUnlockKey.clear()
        val color = context?.let { ContextCompat.getColor(it, R.color.grey) }
        if (color != null) {
            binding.icStar1.background.setTint(color)
        }
        if (color != null) {
            binding.icStar2.background.setTint(color)
        }
        if (color != null) {
            binding.icStar3.background.setTint(color)
        }
        if (color != null) {
            binding.icStar4.background.setTint(color)
        }
    }

    private fun convToEvent(string: String): Event? {
        if (string == "LONG_CLICK") {
            return Event.LONG_CLICK
        }
        if (string == "DOUBLE_TAP") {
            return Event.DOUBLE_TAP
        }
        if (string == "ACCELERATION_CHANGE") {
            return Event.ACCELERATION_CHANGE
        }
        return null
    }

    private fun checkLogin() {
        if (numberOfTries != 0) {
            if (currentUnlockKey.size == 4 && currentUnlockKey == screenUnlockKey) {
                onLoginSuccess()
            } else {
                onLoginFail()
            }
        }
    }

    private fun onLoginSuccess() {
        cleanUp()
        numberOfTries = 3
        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
        loginCallback?.showMap()
        binding.loginButton.isEnabled = false
        isLoggedIn = true

    }

    private fun onLoginFail() {
        numberOfTries -= 1
        cleanUp()
        Toast.makeText(context, "Password incorrect. Attempts left: $numberOfTries", Toast.LENGTH_SHORT).show()
        if (numberOfTries == 0) {
            binding.loginButton.isEnabled = false
            Timer("resetNumberOfTries", true).schedule(5000) {
                numberOfTries = 3
                activity?.runOnUiThread {
                    run() {
                        binding.loginButton.isEnabled = true
                    }
                }
            }
        }
    }

    private fun updateStars() {
        when (currentUnlockKey.size) {
            1 -> starAnimator1?.start()
            2 -> starAnimator2?.start()
            3 -> starAnimator3?.start()
            4 -> starAnimator4?.start()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
//            val sensorName: String = event?.sensor!!.getName();
//            Log.d("Sensor",sensorName + ": X: " + event.values[0] + "; Y: " + event.values[1] + "; Z: " + event.values[2] + ";")
            if (event.values[0] > 5.0 && event.values[1] > 5.0) {
                if (!currentUnlockKey.contains(Event.ACCELERATION_CHANGE) && !isLoggedIn) {
                    currentUnlockKey.add(Event.ACCELERATION_CHANGE)
                    updateStars()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}