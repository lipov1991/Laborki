package pl.lipov.laborki.presentation

import android.text.Editable
import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.lipov.laborki.data.repository.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.UserDto

class AuthorizationViewModel(
        private val loginRepository: LoginRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var userList: List<UserDto>? = null

    fun loginValidator(
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

    fun getUsers() {
        compositeDisposable.add(
                loginRepository.getUsers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            userList = it
                        }, {
                            Log.d("Api Error", it.localizedMessage)
                        })
        )
    }

    fun clearCompositeDisposable() {
        compositeDisposable.clear()
    }

}