package pl.lipov.laborki.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentLoginFirstBinding


class LoginFirstScreen: Fragment() {
    private lateinit var binding: FragmentLoginFirstBinding
    private lateinit var button: Button

    private val loginFirstViewModel: LoginFirstViewModel by viewModels()

    private val compositeDisposable = CompositeDisposable()

    private var loginEntered: CharSequence? = null

    private var loginFirstScreenInterface: LoginFirstScreenInterface? = null



    // private val disposable

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginFirstScreenInterface) { // context = aktywnosc
            loginFirstScreenInterface = context
        }
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
            login(loginEntered.toString())
        }
        binding.login.doOnTextChanged { text, start, before, count ->
            if (text != null) {
                if(text.length > 6 || text.isNullOrEmpty()){
                    binding.login.error = "Login jest za długi"
                    binding.login.setBackgroundResource(R.drawable.edit_text_error_background)
                } else{
                    binding.login.error = null
                    binding.login.setBackgroundResource(R.drawable.edit_text_background)
                    loginEntered = text
                }
            }


        }

    }


    private fun login(
        userName: String
    ){
        compositeDisposable.add(
            loginFirstViewModel.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({users->
                    if(users.findLast { it.name == userName }!= null){
                        loginFirstScreenInterface?.onLoginDBSuccess()

                    }
                    else{
                        loginFirstScreenInterface?.onLoginDBNoUser()
                    }

                }, {
                    loginFirstScreenInterface?.onLoginDBError()
                    //Toast.makeText(this, it.localizedMessage ?: "$it", Toast.LENGTH_LONG).show()
                })
        )

    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}


