package pl.lipov.laborki.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.UserDto

class LoginFirstViewModel(
    private val loginRepository: LoginRepository
):ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var myUsers: List<UserDto>? = null


    fun getUsers(): Single<List<UserDto>> = loginRepository.getUsers()

    fun login(
    ){
        compositeDisposable.add(
                loginRepository.getUsers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({users->
                            myUsers = users
                            //if(users.findLast { it.name == userName }!= null)

                        }, {

                            //Toast.makeText(this, it.localizedMessage ?: "$it", Toast.LENGTH_LONG).show()
                        })
        )
    }

    fun checkLogin(
            myUserDB: String
    ): Boolean {

        if (myUsers != null){
            myUsers?.forEach {
                if(it.name == myUserDB){
                    loginRepository.postPassword(it.unlockKey)
                    return true
                }
            }
        }

        return false

    }

    fun disposableclear(){
        compositeDisposable.clear()
    }


}