package pl.lipov.laborki.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.lang.Exception

class LoginRepository(
    private val loginApi: LoginApi
) {

    val loginResult: MutableLiveData<String> = MutableLiveData()

    fun signIn(
        login: String,
        password: String
    ) {
        try{
            // TODO Login Logic
            loginResult.postValue("Witaj $login")
        } catch (exception: Exception){
            loginResult.postValue(exception.localizedMessage)
        }
    }

}
