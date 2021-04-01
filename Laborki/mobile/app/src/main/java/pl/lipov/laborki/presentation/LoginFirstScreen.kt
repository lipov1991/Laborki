package pl.lipov.laborki.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentLoginFirstBinding


class LoginFirstScreen: Fragment() {
    private lateinit var binding: FragmentLoginFirstBinding
    private lateinit var button: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_first, container, false)
        return binding.root
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)


        binding.loginButton.setOnClickListener {
            (activity as? MainActivity)?.showFragment(LoginFragment())
        }
        binding.login.doOnTextChanged { text, start, before, count ->
            if (text != null) {
                if(text.length < 6 || text.isNullOrEmpty()){
                    binding.login.error = "Login jest za krótki"
                    binding.login.setBackgroundResource(R.drawable.edit_text_error_background)
                } else{
                    binding.login.error = null
                    binding.login.setBackgroundResource(R.drawable.edit_text_background)
                }
            }
        }
    }


}


