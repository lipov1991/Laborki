package pl.lipov.laborki.presentation

interface LoginCallback {
    fun onUsernameSuccess()
    fun onUsernameError()
    fun onPasswordSuccess()
    fun onPasswordError()
    fun onAttemptLimit()

}