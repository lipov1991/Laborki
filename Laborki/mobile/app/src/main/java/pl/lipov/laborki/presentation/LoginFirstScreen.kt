package pl.lipov.laborki.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import pl.lipov.laborki.data.repository.api.dto.UserDto
import pl.lipov.laborki.databinding.FragmentLoginFirstBinding


class LoginFirstScreen: Fragment() {
    private lateinit var binding: FragmentLoginFirstBinding
    private lateinit var button: Button

    private val loginFirstViewModel by inject<LoginFirstViewModel>()

    private val compositeDisposable = CompositeDisposable()

    private var loginEntered: CharSequence? = null

    private var loginFirstScreenInterface: LoginFirstScreenInterface? = null

    //private val mainViewModel: MainViewModel by activityViewModels()
    //private val mainViewModel by inject<MainViewModel>()

    private val viewModel: LoginFirstViewModel by viewModel()

    private var loginStatus: Boolean = false

    private var myUsers: List<UserDto>? = null



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

            if (viewModel.checkLogin(loginEntered.toString())) {
                (activity as? MainActivity)?.showFragment(LoginFragment())
                loginFirstScreenInterface?.onLoginDBSuccess()
            }
            else{
                loginFirstScreenInterface?.onLoginDBNoUser()
            }

        }
        loginEntered="user_1"
        viewModel.login()

        binding.login.doOnTextChanged { text, start, before, count ->
            if (text != null) {
                if(text.length < 5 || text.isNullOrEmpty()){
                    binding.login.error = "Login jest za krótki"
                    binding.login.setBackgroundResource(R.drawable.edit_text_error_background)
                } else{
                    binding.login.error = null
                    binding.login.setBackgroundResource(R.drawable.edit_text_background)
                    loginEntered = text

                }
            }

        }

    }


//    private fun login(
//        userName: String
//
//    ){
//        Log.i("log", "fun")
//        compositeDisposable.add(
//            loginFirstViewModel.getUsers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({users->
//
////                    for (item in users){
////                        Log.i("log", item.name )
//                    //}
//                    myUsers = users
//
//
//                    if(users.findLast { it.name == userName }!= null){
//                        loginFirstScreenInterface?.onLoginDBSuccess()
//                        Log.i("log", "udało sie")
//
//                        loginStatus = true
//                    }
//                    else{
//                        loginFirstScreenInterface?.onLoginDBNoUser()
//                        Log.i("log", "no user")
//                    }
//
//                }, {
//                    loginFirstScreenInterface?.onLoginDBError()
//                    Log.i("log", "error")
//                    //Toast.makeText(this, it.localizedMessage ?: "$it", Toast.LENGTH_LONG).show()
//                })
//        )
//
//    }

    override fun onDestroy() {
        //compositeDisposable.clear()
        viewModel.clear2()
        super.onDestroy()
    }

}


