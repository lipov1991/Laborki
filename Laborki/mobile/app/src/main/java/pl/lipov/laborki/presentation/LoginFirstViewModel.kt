package pl.lipov.laborki.presentation

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.repository.api.dto.UnlockKeyDto
import pl.lipov.laborki.data.repository.api.dto.UserDto
//import pl.lipov.laborki.presentation.map.MapActivity


class LoginFirstViewModel (

    private val loginRepository: LoginRepository,



) : ViewModel(){

    private val compositeDisposable = CompositeDisposable() // asych
    private var loginFirstScreenInterface: LoginFirstScreenInterface? = null
    private var myUsers: List<UserDto>? = null
    //private var myPass: UnlockKeyDto? = null
    var myPass = loginRepository.unlockKey

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

                }, {
                    Log.i("log", "error")
                    //Toast.makeText(this, it.localizedMessage ?: "$it", Toast.LENGTH_LONG).show()
                })
        )

    }

    fun checkLogin(
        myUserDB: String
    ): Boolean {
        myUsers?.forEach{

            if (it.name == myUserDB){
                //myPass = it.unlockKey
                postPassword(it.unlockKey)
                return true
            }
        }
        return false

    }

    fun postPassword(unlockkey:UnlockKeyDto){
        val listEvent = listOf(
            convertStringToEvent(unlockkey.event1),
            convertStringToEvent(unlockkey.event2),
            convertStringToEvent(unlockkey.event3),
            convertStringToEvent(unlockkey.event4)
        )

        myPass.postValue(listEvent)

    }

    private fun convertStringToEvent(event: String):Event{
        if(event=="LONG_CLICK")
            return Event.LONG_CLICK
        else if(event=="DOUBLE_TAP")
            return Event.DOUBLE_TAP
        else
            return Event.ACCELERATION_CHANGE

    }


    fun clear2(){
        compositeDisposable.clear()

    }
//
//    private fun successfulLogin(){
//        activity?.let{parent}
//        val intent = Intent(this, MapActivity::class.java) // knteks, klasa aktywnosci docelowej
//        startActivity(intent)
//    }

}