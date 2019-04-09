package pl.krzysztof.lipka.laborki.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.hannesdorfmann.mosby.mvp.MvpView

abstract class BaseFragment<V : MvpView, P : MvpBasePresenter<V>>(
    private val layoutResId: Int
) : MvpFragment<V, MvpBasePresenter<V>>() {

    protected abstract val viewPresenter: P
    protected var shareDataListener: ShareDataListener? = null

    override fun createPresenter() = viewPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ShareDataListener) {
            shareDataListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutResId, container, false)
}
