package pl.lipov.laborki.data

import androidx.fragment.app.Fragment

interface ViewRouter {
    fun navigateTo(fragment: Fragment)
}