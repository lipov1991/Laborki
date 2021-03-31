package pl.lipov.laborki.presentation
import androidx.fragment.app.Fragment

interface ViewRouter {
    fun navigateTo(fragment: Fragment)
}