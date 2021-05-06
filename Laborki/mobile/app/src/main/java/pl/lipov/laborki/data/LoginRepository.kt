package pl.lipov.laborki.data

import android.app.usage.UsageEvents
import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.model.Gallery
import pl.lipov.laborki.data.repository.api.Api
import pl.lipov.laborki.data.repository.api.dto.UnlockKeyDto
import pl.lipov.laborki.data.repository.api.dto.UserDto
import retrofit2.Call

class LoginRepository(
    private val api: Api
) {


    var password: List<Event>? = null
    val getEvent: MutableLiveData<Event> = MutableLiveData()

    fun getUsers(): Single<List<UserDto>> = api.getUsers()

    fun getGalleries(): Single<List<Gallery>> = api.getGalleries()

    fun postPassword(unlockkey:UnlockKeyDto){
        val listEvent = listOf(
                convertStringToEvent(unlockkey.event1),
                convertStringToEvent(unlockkey.event2),
                convertStringToEvent(unlockkey.event3),
                convertStringToEvent(unlockkey.event4)
        )

        password = listEvent

        Log.i("LOG","dziddsadsadsa")

    }

    private fun convertStringToEvent(event: String):Event{
        if(event=="LONG_CLICK")
            return Event.LONG_CLICK
        else if(event=="DOUBLE_TAP")
            return Event.DOUBLE_TAP
        else
            return Event.ACCELERATION_CHANGE

    }

}
