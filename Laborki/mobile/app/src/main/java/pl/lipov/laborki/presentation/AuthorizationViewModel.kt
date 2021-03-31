package pl.lipov.laborki.presentation

import android.text.Editable
import androidx.lifecycle.ViewModel

class AuthorizationViewModel(

) : ViewModel() {

    val login = "Pikachu"

    fun loginValidator(
            loginEntered: Editable?
    ): Boolean {
        if (loginEntered.toString() == login) {
            return true
        }
        return false
    }
}