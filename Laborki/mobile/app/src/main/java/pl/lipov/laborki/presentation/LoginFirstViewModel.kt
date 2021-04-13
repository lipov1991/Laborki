package pl.lipov.laborki.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.UserDto

class LoginFirstViewModel(
    private val loginRepository: LoginRepository
):ViewModel() {

    fun getUsers(): Single<List<UserDto>> = loginRepository.getUsers()

}