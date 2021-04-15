package pl.lipov.laborki.presentation

import android.text.Editable
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.lipov.laborki.data.repository.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.UserDto

class UsernameViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var usersList: List<UserDto>? = null

    fun getUsers() {
        compositeDisposable.add(
            loginRepository.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    usersList = it
                }, {})
        )
    }

    fun checkLogin(
        login: Editable?
    ): Boolean {
        usersList?.forEach {
            if (it.name == login.toString()) {
                loginRepository.screenUnlockKey = it.unlockKey
                return true
            }
        }
        return false
    }
}