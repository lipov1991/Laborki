package pl.lipov.laborki.presentation.login

import android.app.usage.UsageEvents
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.repository.api.dto.UserDto
import pl.lipov.laborki.presentation.LoginStatus

class LoginFragmentViewModel(
        private val loginRepository: LoginRepository
) : ViewModel() {

    val password = loginRepository.password
    private val userSeq = mutableListOf<Event>()
    private var counter = 0
    val loginStatus = MutableLiveData<LoginStatus>()
    val getEvent: MutableLiveData<Event> = loginRepository.getEvent


    fun addEvent(event: Event){
        userSeq.add(event)
        if (userSeq.size == password?.size) {
            if(userSeq==password){
                counter = 0
                loginStatus.postValue(LoginStatus.CORRECT)
            }
            else{
                if(counter < 2){
                    loginStatus.postValue(LoginStatus.UNCORRECT)
                }
                counter++
            }
            userSeq.clear()

        }
        if(counter == 3){
            loginStatus.postValue(LoginStatus.BLOCKED)
        }
    }

}