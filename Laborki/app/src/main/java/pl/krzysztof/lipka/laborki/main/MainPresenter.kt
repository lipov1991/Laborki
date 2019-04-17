package pl.krzysztof.lipka.laborki.main

import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val view: MainView
) {

    fun receiveTestMessage() = view.onTestMessageReceived()
}
