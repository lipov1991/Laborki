package pl.lipov.laborki.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import pl.lipov.laborki.R
import pl.lipov.laborki.data.ViewRouter
import pl.lipov.laborki.databinding.FragmentLoginTextBinding

class TextFragment : Fragment() {

    private val sharedVM: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginTextBinding
    private var viewRouter: ViewRouter? = null
    private val correctLogin = "Antonijestfajny"
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
            inflater, R.layout.fragment_login_text, container,
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
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d("tag", s.toString())
                    if (s.toString() == correctLogin) {
                        viewRouter?.navigateTo(LoginFragment())
                    }
                    else {
                        if (s.toString().length >= correctLogin.length) {
                            binding.editTextLogin.setBackgroundResource(R.drawable.edit_text_error_background)
                            binding.editTextLogin.error = "Hasło niepoprawne"
                        }
                    }
                }
            }
        )

    }
}