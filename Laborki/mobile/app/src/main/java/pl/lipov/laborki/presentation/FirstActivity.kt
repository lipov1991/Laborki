package pl.lipov.laborki.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.first_view.*
import pl.lipov.laborki.R
class FirstActivity: AppCompatActivity() {
//    private var disableTime: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_view)
        val intent = Intent(this, MainActivity::class.java)
        var extras:Bundle? = getIntent().getExtras()
        var delayTime:Long = 0

        if (extras != null) {
            unlock_button.isEnabled = false
            delayTime = extras.getLong("Delay", 10000)
            println(delayTime.toString())
            Handler().postDelayed({
                unlock_button.isEnabled = true
            }, delayTime)
        }
        unlock_button.setOnClickListener {

//            Bundle extras = getIntent().getExtras()
//            disableTime = intent.getIntExtra("Disabled for seconds")


            startActivity(intent)
            setContentView(R.layout.unlock_activity)

        }
    }

}