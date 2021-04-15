package pl.lipov.laborki.presentation

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.reactivex.disposables.CompositeDisposable
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentLoginUsernameBinding

class UsernameFragment : Fragment() {

    private val compositeDisposable = CompositeDisposable()
    private var loginCallback: LoginCallback? = null
    private val viewModel: UsernameViewModel by viewModels()
    private lateinit var binding: FragmentLoginUsernameBinding
    private var viewRouter: ViewRouter? = null

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

        viewModel.getUsers()
        binding.editTextLogin.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (viewModel.checkLogin(binding.editTextLogin.text)) {
                    binding.editTextLogin.setBackgroundResource(R.drawable.edit_text_background)
                    loginCallback?.onUsernameSuccess()
                    viewRouter?.navigateTo(LoginFragment())
                } else {
                    binding.editTextLogin.setBackgroundResource(R.drawable.edit_text_error_background)
                    loginCallback?.onUsernameError()
                }
                return@setOnKeyListener true
            }
            false
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}