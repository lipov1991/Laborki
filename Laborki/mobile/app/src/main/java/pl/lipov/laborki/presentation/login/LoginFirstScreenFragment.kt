package pl.lipov.laborki.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentLoginFirstScreenBinding
import pl.lipov.laborki.presentation.JsonPlaceHolderApi
import pl.lipov.laborki.presentation.LoginFirstViewModel

import pl.lipov.laborki.presentation.MainActivity
import pl.lipov.laborki.presentation.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


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
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = service.getPosts()
        call.enqueue(object: Callback<List<Post>>{
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                TODO("Not yet implemented")
                Toast.makeText(activity,"nie udalo sie",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                TODO("Not yet implemented")

                    var post = response.body()
                    Toast.makeText(activity,"udalo sie",Toast.LENGTH_LONG).show()


            }

        })
        binding.buttonLogin.setOnClickListener{
            if (login){
                login("byleco")
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