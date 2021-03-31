package pl.lipov.laborki.presentation

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.ViewRouter
import pl.lipov.laborki.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment() {

    private val viewModel: AuthorizationViewModel by viewModel()
    private lateinit var binding: FragmentAuthorizationBinding
    private var viewRouter: ViewRouter? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ViewRouter) {
            viewRouter = context
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_authorization, container, false)
        return binding.root
    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)


        binding.editTextLogin.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (viewModel.loginValidator(binding.editTextLogin.text)) {
                    binding.editTextLogin.setBackgroundResource(R.drawable.edit_text_background)
                    Toast.makeText(context, "Login poprawny", Toast.LENGTH_LONG).show()
                    viewRouter?.navigateTo(LoginFragment())
                } else {
                    binding.editTextLogin.setBackgroundResource(R.drawable.edit_text_error_background)
                    binding.editTextLogin.error = "Login niepoprawny"
                }
                binding.root.hideKeyboard()
                return@setOnKeyListener true
            }
            false
        }


    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}