package pl.lipov.laborki.data

import androidx.lifecycle.MutableLiveData
import pl.lipov.laborki.data.model.Event

class LoginRepository(
        private val loginApi: LoginApi
) {

    val attemptEnterPassword = MutableLiveData<Event>()

}
