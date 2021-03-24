package pl.lipov.laborki.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.data.model.Event

class ConnectViewModel: ViewModel() {
    val getEvent: MutableLiveData<Event> = MutableLiveData()
}