package pl.lipov.laborki.presentation.training

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import pl.lipov.laborki.R

class TrainingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        showFragment(FirstFragment())
    }

    fun showFragment(
        fragment: Fragment
    ) {
        supportFragmentManager.beginTransaction().run {
            setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.fade_out)
            replace(R.id.root_container, fragment)
            commit()
        }
    }
}
