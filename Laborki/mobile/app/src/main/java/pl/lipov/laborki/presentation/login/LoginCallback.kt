package pl.lipov.laborki.presentation.login

interface LoginCallback {

    fun onLoginSuccess()
    fun onUnsuccess()
    fun blocked()

}