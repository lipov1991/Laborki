package pl.lipov.laborki.presentation

interface LoginFirstScreenInterface {
    fun onLoginDBSuccess()
    fun onLoginDBNoUser()
    fun onLoginDBError()
}