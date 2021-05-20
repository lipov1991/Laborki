package pl.lipov.laborki.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.model.LoginStatus
import pl.lipov.laborki.data.repository.LoginRepository

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    var incorrectLoginAttempts: Int = 0
    private var currentSampleList: MutableList<Event> = mutableListOf()
    val onEvent: MutableLiveData<Event> = loginRepository.attemptEnterPassword


    private val screenUnlockKey: List<Event> =
        listOf(
            Event.valueOf(loginRepository.screenUnlockKey.event1),
            Event.valueOf(loginRepository.screenUnlockKey.event2),
            Event.valueOf(loginRepository.screenUnlockKey.event3),
            Event.valueOf(loginRepository.screenUnlockKey.event4)
        )

    fun addCurrentSample(
        event: Event
    ) {
        currentSampleList.add(event)
    }

    fun passwordInput(): Int {
        return currentSampleList.size - 1
    }

    fun passwordCheck(): LoginStatus? {
        if (currentSampleList.size == 4) {

            if (currentSampleList == screenUnlockKey) {
                return LoginStatus.SUCCESS
            }
            incorrectLoginAttempts += 1
            currentSampleList.clear()

            if (incorrectLoginAttempts == 3) return LoginStatus.BLOCK

            return LoginStatus.UNSUCCESS

        }
        return null
    }

}
