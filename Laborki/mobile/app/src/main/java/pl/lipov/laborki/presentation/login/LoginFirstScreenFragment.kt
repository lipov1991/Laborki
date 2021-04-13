package pl.lipov.laborki.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentLoginFirstScreenBinding
import pl.lipov.laborki.presentation.LoginFirstViewModel

import pl.lipov.laborki.presentation.MainActivity


class LoginFirstScreenFragment: Fragment() {

    private lateinit var binding: FragmentLoginFirstScreenBinding
    private var login = false
    private val viewModel by inject<LoginFirstViewModel>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_first_screen, container, false)
        return binding.root
    }



    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener{
            if (login){
                (activity as? MainActivity)?.showFragment(LoginFragment())
            }
        }
        binding.textSpace.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                login = false
                binding.textSpace.error = "To pole nie może być puste"
                binding.textSpace.setBackgroundResource(R.drawable.edit_text_error_background)
            }
            else if(text.length < 8){
                login = false
                binding.textSpace.error = "Login jest za krotki"
                binding.textSpace.setBackgroundResource(R.drawable.edit_text_error_background)
            }
            else {
                login = true
                binding.textSpace.error = null
                binding.textSpace.setBackgroundResource(R.drawable.edit_text_background)
            }
        }

    }

    private fun login(
        userName: String
    ){
        compositeDisposable.add(
            viewModel.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({users->
                    //if(users.findLast { it.name == userName }!= null)
                    //TODO onSuccess
                }, {
                    //TODO onError
                    //Toast.makeText(this, it.localizedMessage ?: "$it", Toast.LENGTH_LONG).show()
                })
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}