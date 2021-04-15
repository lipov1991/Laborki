package pl.lipov.laborki.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.UserDto



class LoginFirstViewModel (

    private val loginRepository: LoginRepository



) : ViewModel(){

    private val compositeDisposable = CompositeDisposable()
    private var loginFirstScreenInterface: LoginFirstScreenInterface? = null
    private var myUsers: List<UserDto>? = null

    fun getUsers(): Single<List<UserDto>> = loginRepository.getUsers()

    fun login(
        //userName: String

    ){
        Log.i("log", "fun")
        compositeDisposable.add(
            loginRepository.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({users->
                    myUsers = users

                    //if(users.findLast { it.name == userName }!= null){
                        //loginFirstScreenInterface?.onLoginDBSuccess()
                        Log.i("log", "udało sie")

                        //loginStatus = true
//                    }
//                    else{
//                        loginFirstScreenInterface?.onLoginDBNoUser()
//                        Log.i("log", "no user")
//                    }

                }, {
                   // loginFirstScreenInterface?.onLoginDBError()
                    Log.i("log", "error")
                    //Toast.makeText(this, it.localizedMessage ?: "$it", Toast.LENGTH_LONG).show()
                })
        )

    }

    fun checkLogin(
        myUserDB: String
    ): Boolean {
        myUsers?.forEach{
            return it.name == myUserDB
        }
        return false

    }

//    fun checkPass(): Boolean {
//
//        if(loginRepository.screenUnlockKey == ) {
//            return true
//        }
//        return false
//
//    }

    fun clear2(){
        compositeDisposable.clear()

    }

}