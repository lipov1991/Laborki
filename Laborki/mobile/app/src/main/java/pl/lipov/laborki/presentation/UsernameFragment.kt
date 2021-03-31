package pl.lipov.laborki.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentLoginUsernameBinding

class UsernameFragment : Fragment() {

    private val svm: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginUsernameBinding
    private var viewRouter: ViewRouter? = null
    private val rightUsername = "kochampmag"

    override fun onAttach(
        context: Context
    ) {
        super.onAttach(context)
        if (context is ViewRouter) {
            viewRouter = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login_username, container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.editTextLogin.addTextChangedListener(
            object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(username: CharSequence?, start: Int, before: Int, count: Int) {
                    if (username.toString() == rightUsername) {
                        viewRouter?.navigateTo(LoginFragment())
                    }
                    else {
                        if (username.toString().length >= rightUsername.length) {
                            binding.editTextLogin.error = "Niepoprawna nazwa użytkownika"
                            binding.editTextLogin.setBackgroundResource(R.drawable.edit_text_error_background)
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            }
        )
    }
}