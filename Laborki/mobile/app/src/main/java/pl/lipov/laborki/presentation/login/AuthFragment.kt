package pl.lipov.laborki.presentation.login

import android.animation.ValueAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.FragmentAuthBinding
import pl.lipov.laborki.presentation.MainActivity
import pl.lipov.laborki.presentation.MainViewModel
import java.util.*
import kotlin.concurrent.schedule

class AuthFragment: Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAuthBinding
    private var authCallback: AuthCallback? = null
    private val listOfLogins = listOf<String>("jacksonafide", "pawixior", "admin")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AuthCallback) {
            authCallback = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var loginButton2 = activity?.findViewById(R.id.login_button_2) as Button
        var loginForm = activity?.findViewById(R.id.login_form) as EditText
        loginButton2.setOnClickListener {
            loginAuthentication(loginForm.text.toString())
        }
    }

    private fun loginAuthentication(userLoginInput: String) {
        if (listOfLogins.contains(userLoginInput)){
            authCallback?.onLoginSuccess()
        } else {
            Toast.makeText(context, "Login incorrect", Toast.LENGTH_SHORT).show()
        }
    }
}