package pl.lipov.laborki.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentLoginFirstBinding
import pl.lipov.laborki.presentation.login.LoginFragment
import pl.lipov.laborki.presentation.main.MainActivity
import pl.lipov.laborki.presentation.main.MainViewModel


class LoginFirstScreen: Fragment() {
    private lateinit var binding: FragmentLoginFirstBinding
    private lateinit var button: Button
    private val compositeDisposable = CompositeDisposable()
    private val viewModel: MainViewModel by viewModel()

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



//        compositeDisposable.add(
//            viewModel.getUsers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    var message = ""
//                    it.forEach { user ->
//                        message += "${user.name}: ${user.unlockKey.event1}" +
//                                "${user.unlockKey.event2}, ${user.unlockKey.event3}" +
//                                "${user.unlockKey.event4}\n\n"
//
//                    }
//                }, {
//                   //  binding.textView.text = message
//                   //  binding.textView.text = it.LocalizedMessage?: "$it"
//                    //Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//
//                })
//        )




        binding.loginButton.setOnClickListener {
            (activity as? MainActivity)?.showFragment(LoginFragment())
        }
        binding.login.doOnTextChanged { text, start, before, count ->
            if (text != null) {
                if(text.length != 6 || text.isNullOrEmpty()){
                    binding.login.error = "Login jest nieprawidlowy"
                } else{
                    binding.login.error = null
                }
            }
        }


    }
    fun onDestory()
    {
        compositeDisposable.clear()
        super.onDestroy()
    }

//    private fun login(userName: String)
//    {
//        compositeDisposable.add(
//            viewModel.getUsers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    var message = ""
//                    it.forEach { user ->
//                        message += "${user.name}: ${user.unlockKey.event1}" +
//                                "${user.unlockKey.event2}, ${user.unlockKey.event3}" +
//                                "${user.unlockKey.event4}\n\n"
//
//                    }
//                }, {
//                   //  binding.textView.text = message
//                   //  binding.textView.text = it.LocalizedMessage?: "$it"
//                    //Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//
//                })
//        )
//   }


}


