package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat.setTint
import kotlinx.android.synthetic.main.activity_training.*
import pl.lipov.laborki.R

class TrainingActivity : AppCompatActivity() {

    private var starAnimator: ValueAnimator? = null
    private var borderedStarAnimator: ValueAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        borderedStarAnimator = ic_bordered_star
            .getBackgroundAnimator()
            .apply {
                doOnStart {
                    play_button.isEnabled = false
                }
                doOnEnd {
                    starAnimator?.start()
                }
            }
        starAnimator = ic_star
            .getBackgroundAnimator(duration = 1000)
            .apply {
                doOnEnd {
                    play_button.isEnabled = true
                }
            }
        play_button.setOnClickListener {
            borderedStarAnimator?.start()
        }
    }

    private fun View.getBackgroundAnimator(
        duration: Long = 500,
        @ColorRes firstColorResId: Int = R.color.army,
        @ColorRes secondColorResId: Int = R.color.grey
    ): ValueAnimator {
        return ValueAnimator.ofArgb(
            ContextCompat.getColor(context, firstColorResId),
            ContextCompat.getColor(context, secondColorResId)
        ).apply {
            addUpdateListener {
                setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }
}
