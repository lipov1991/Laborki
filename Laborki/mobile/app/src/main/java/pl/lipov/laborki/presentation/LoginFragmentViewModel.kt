package pl.lipov.laborki.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.repository.api.dto.UnlockKeyDto

class LoginFragmentViewModel (

    private val loginRepository: LoginRepository

): ViewModel() {

    var myPass = loginRepository.unlockKey

}