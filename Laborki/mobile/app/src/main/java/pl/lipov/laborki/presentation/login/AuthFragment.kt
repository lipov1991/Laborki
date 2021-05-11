package pl.lipov.laborki.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentAuthBinding
import pl.lipov.laborki.presentation.main.MainViewModel

class AuthFragment: Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAuthBinding
    private var authCallback: AuthCallback? = null
    val database = Firebase.database
    val dbRef = database.getReference()

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
        if (userLoginInput.isEmpty() == false) {
            dbRef.child(userLoginInput).get().addOnSuccessListener {
                if (it.value == null) {
                    Toast.makeText(context, "Login incorrect", Toast.LENGTH_SHORT).show()
                } else {
                    authCallback?.onLoginSuccess()
                    viewModel.login = userLoginInput
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Error connecting to database", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Login is empty!", Toast.LENGTH_SHORT).show()
        }
    }
}