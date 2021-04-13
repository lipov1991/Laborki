package pl.lipov.laborki.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import pl.lipov.laborki.common.utils.GestureDetectorUtils
import pl.lipov.laborki.common.utils.SensorEventsUtils
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.repository.api.dto.UserDto

class MainViewModel( // pozwala na przesyalnie miedzy aktywnosciami aktywanosciami i fragmntami
    private val gestureDetectorUtils: GestureDetectorUtils,
    private val sensorEventsUtils: SensorEventsUtils,
    private val loginRepository: LoginRepository
) : ViewModel() {

    val onAccelerometerNotDetected: MutableLiveData<Unit> =
        sensorEventsUtils.onAccelerometerNotDetected
    val onGestureEvent: MutableLiveData<Event> = gestureDetectorUtils.onEvent
    val onSensorEvent: MutableLiveData<Event> = sensorEventsUtils.onEvent

    fun getUsers(): Single<List<UserDto>> = loginRepository.getUsers()
}
