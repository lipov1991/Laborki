package pl.krzysztof.lipka.laborki.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import pl.krzysztof.lipka.laborki.R
import pl.krzysztof.lipka.laborki.common.BaseActivity
import pl.krzysztof.lipka.laborki.main.recipient_data.RecipientDataFragment

class MainActivity : BaseActivity(), MainView {

    companion object {
        private const val MAIN_SCREEN_BACK_STACK_ENTRY_COUNT = 1
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBar()
        RecipientDataFragment().show()
        back_icon.setOnClickListener {
            onBackPressed()
        }
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

    override fun onBackPressed() =
        if (supportFragmentManager.backStackEntryCount == MAIN_SCREEN_BACK_STACK_ENTRY_COUNT) {
            // TODO
//            showAlertDialog(
//                context = this,
//                alertDialogParams = AlertDialogParamTypes.EXIT_APP_CONFIRMATION.value,
//                positiveButtonClickListener = DialogInterface.OnClickListener { _, _ ->
//                    finish()
//                }
//            )
        } else {
            super.onBackPressed()
        }
}
