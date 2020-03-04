package pl.lipov.laborki.presentation

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        viewModel.initGestureDetector(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.run {
            onAccelerometerNotDetected.observe(::getLifecycle) {
                info_text.text = it
            }
            onGestureEvent.observe(::getLifecycle) {
                info_text.text = it.name
            }
            onSensorEvent.observe(::getLifecycle) {
                info_text.text = it.name
            }
        }
    }
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean
    {
        viewModel.onTouchEvent(motionEvent)
        return super.onTouchEvent(motionEvent)
    }
}
