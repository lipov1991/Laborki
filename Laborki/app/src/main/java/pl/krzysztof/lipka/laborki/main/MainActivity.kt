package pl.krzysztof.lipka.laborki.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hannesdorfmann.mosby.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_main.*
import pl.krzysztof.lipka.laborki.R
import pl.krzysztof.lipka.laborki.main.recipient_data.RecipientDataFragment

class MainActivity : MvpActivity<MainView, MainPresenter>(), MainView {

    override fun createPresenter() = MainPresenter()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        // TODO lab1
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBar()
        RecipientDataFragment().show()
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun Fragment.show() =
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, this)
            .addToBackStack(null)
            .commit()
}
