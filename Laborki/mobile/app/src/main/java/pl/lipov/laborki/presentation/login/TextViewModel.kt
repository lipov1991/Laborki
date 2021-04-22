package pl.lipov.laborki.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.lipov.laborki.data.repository.api.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.UserDto

class TextViewModel(
    val loginRepository: LoginRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var userList: List<UserDto>? = null

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