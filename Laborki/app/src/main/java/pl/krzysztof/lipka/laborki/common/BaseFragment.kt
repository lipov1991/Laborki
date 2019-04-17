package pl.krzysztof.lipka.laborki.common

import android.content.Context
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    override fun onAttach(
        context: Context
    ) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
