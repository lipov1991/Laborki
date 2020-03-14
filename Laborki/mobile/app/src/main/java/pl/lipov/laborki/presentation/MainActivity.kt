package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
//import kotlinx.android.synthetic.main.activity_main.info_text
import kotlinx.android.synthetic.main.first_view.*
import kotlinx.android.synthetic.main.unlock_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import java.util.*
import kotlin.collections.ArrayList

class MainActivity: AppCompatActivity() {
    private var currentAnimator: ValueAnimator? = null

    private val unlockedSuccessfullyText: String = "Odblokowano urzadzenie"
    private val unlockSequence = listOf<String>("DOUBLE_TAP", "DOUBLE_TAP", "LONG_CLICK", "ACCELERATION_CHANGE")
    @ColorRes private val trueColor = R.color.green
    @ColorRes private val defColor = R.color.black
    private var currentIn : Int = 0
    private var remainedTries: Int = 3
    private val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel.initGestureDetector(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.unlock_activity)
//        unlock_sequence_text.isVisible = true
        viewModel.run {

//            onGestureEvent.removeObservers((::getLifecycle))
            information_text.text = getString(R.string.unlock_device_text)
            val dotSeqeunce = arrayListOf<View>(first_seq, second_seq, third_seq, fourth_seq)

            onAccelerometerNotDetected.observe(::getLifecycle) {
//                info_text.text = it
            }

            onGestureEvent.observe(::getLifecycle) {
                unlockingDevice(it.name, dotSeqeunce)
            }
            onSensorEvent.observe(::getLifecycle) {
                unlockingDevice(it.name, dotSeqeunce)
            }
        }
    }
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean
    {
        viewModel.onTouchEvent(motionEvent)
        return super.onTouchEvent(motionEvent)
    }

    private fun View.getBackgroundAnimator(
        duration: Long = 100,

        @ColorRes ColorResId: Int = R.color.grey
    ): ValueAnimator {
        return ValueAnimator.ofArgb(
            ContextCompat.getColor(context, ColorResId)
        ).apply {
            addUpdateListener {
                DrawableCompat.setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }

    private fun unlockingDevice(eventName: String, sequenceArray: ArrayList<View>): Void? {
        var curSeq: String = unlockSequence.get(currentIn)
        if (eventName == curSeq) {

            currentAnimator = sequenceArray.get(currentIn)
                    .getBackgroundAnimator(ColorResId = trueColor)
                    .apply {

                    }
            currentAnimator?.start()

            currentIn += 1
            currentAnimator?.end()
            if (currentIn == 4) {

                for (item in sequenceArray) {
                    item.isVisible = false
                }
                information_text.text = unlockedSuccessfullyText
                currentIn = 0
            }
            currentAnimator?.end()
        } else {
            currentIn = 0
            remainedTries -= 1
            remaining_tries.isVisible = true
            var textInfo:String = getString(R.string.num_of_remaining_attempts, remainedTries)
            remaining_tries.text = textInfo
            for (item in sequenceArray) {
                currentAnimator = item
                        .getBackgroundAnimator(ColorResId = defColor)
                        .apply {

                        }
                currentAnimator?.start()
                currentAnimator?.end()
            }
            if (remainedTries == 0) {
                val noMoreTriesText: String = "Nie pozostalo zadnych prob. "
                remaining_tries.isVisible=false
                information_text.text = noMoreTriesText
                val intent = Intent(this, FirstActivity::class.java)
                val delayTime:Long = 1000
                intent.putExtra("Delay", delayTime)

                startActivity(intent)
                setContentView(R.layout.first_view)

            }
        }
        return null
    }
}