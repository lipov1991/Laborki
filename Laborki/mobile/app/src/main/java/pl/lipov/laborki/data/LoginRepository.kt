package pl.lipov.laborki.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.model.Gallery
import pl.lipov.laborki.data.repository.api.Api
import pl.lipov.laborki.data.repository.api.dto.UnlockKeyDto
import pl.lipov.laborki.data.repository.api.dto.UserDto

class LoginRepository(
    private val api: Api
) {

    lateinit var screenUnlockKey: UnlockKeyDto


    fun getUsers(): Single<List<UserDto>> {
        Log.i("log", "LoginRepository")
        return api.getUsers()
    }

    val unlockKey: MutableLiveData<List<Event>> = MutableLiveData()

    fun getGallery(): Single<List<Gallery>> = api.getGallery()
}
