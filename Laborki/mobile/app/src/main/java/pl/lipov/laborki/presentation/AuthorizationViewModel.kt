package pl.lipov.laborki.presentation

import android.text.Editable
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import pl.lipov.laborki.data.repository.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.UserDto

class AuthorizationViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {


    var userList: List<UserDto>? = null

    fun loginValidate(
        loginEntered: Editable?
    ): Boolean {
        userList?.forEach {
            if (it.name == loginEntered.toString()) {
                loginRepository.screenUnlockKey = it.unlockKey
                return true
            }
        }
        return false
    }

    fun getUsers(): Single<List<UserDto>> {
        return loginRepository.getUsers()
    }

}