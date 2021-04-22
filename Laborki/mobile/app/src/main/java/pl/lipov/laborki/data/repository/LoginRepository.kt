package pl.lipov.laborki.data.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.repository.api.Api
import pl.lipov.laborki.data.repository.api.dto.UnlockKeyDto
import pl.lipov.laborki.data.repository.api.dto.UserDto

class LoginRepository(
    private val api: Api
) {

    val attemptEnterPassword = MutableLiveData<Event>()

    lateinit var screenUnlockKey: UnlockKeyDto

    fun getUsers(): Single<List<UserDto>> = api.getUsers()

}
