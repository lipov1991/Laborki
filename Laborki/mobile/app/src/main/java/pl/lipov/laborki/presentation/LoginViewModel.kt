package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.model.LoginSuccess
import pl.lipov.laborki.data.repository.LoginRepository

class LoginViewModel(
        private val loginRepository: LoginRepository
) : ViewModel() {

    var incorrectLoginAttempts: Int = 0
    private var currentSampleList: MutableList<Event> = mutableListOf()
    val onEvent: MutableLiveData<Event> = loginRepository.attemptEnterPassword


    private val screenUnlockKey: List<Event> =
            listOf(
                    Event.valueOf(loginRepository.screenUnlockKey.event1),
                    Event.valueOf(loginRepository.screenUnlockKey.event2),
                    Event.valueOf(loginRepository.screenUnlockKey.event3),
                    Event.valueOf(loginRepository.screenUnlockKey.event4)
            )

    fun addCurrentSample(
            event: Event
    ) {
        currentSampleList.add(event)
    }

    fun passwordInput(
            list: List<View>
    ) {
        when (currentSampleList.size) {
            1 -> list[0].setIconAnimator().start()
            2 -> list[1].setIconAnimator().start()
            3 -> list[2].setIconAnimator().start()
            4 -> list[3].setIconAnimator().start()
        }
    }

    fun passwordCheck(
            list: List<View>
    ): Pair<Int?, String?>? {
        if (currentSampleList.size == 4) {
            if (currentSampleList == screenUnlockKey) {
                list.forEachIndexed { index, element -> element.getTintAnimator(duration = ((index + 1) * 500).toLong()).start() }
                return Pair(1, LoginSuccess.SUCCESS.value)
            }
            incorrectLoginAttempts += 1
            list.forEach { it.setIconAnimator(IconFromId = R.drawable.ic_star, IconToId = R.drawable.ic_star_border).start() }
            currentSampleList.clear()

            if (incorrectLoginAttempts == 3) {
                return Pair(1, LoginSuccess.BLOCK.value)
            }

            return Pair(null, "${LoginSuccess.UNSUCCESS.value} ${3 -incorrectLoginAttempts}")
        }
        return null
    }

    //    Przed nazwą funkcji wskazany typ klasy która rozszerzamy
    private fun View.getTintAnimator(
            duration: Long = 500,
            @ColorRes ColorFromId: Int = R.color.army,
            @ColorRes ColorToId: Int = R.color.gold
    ): ValueAnimator {
        return ValueAnimator.ofArgb(
                ContextCompat.getColor(context, ColorFromId),
                ContextCompat.getColor(context, ColorToId)
        ).apply {
            addUpdateListener {
                DrawableCompat.setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }

    private fun View.setIconAnimator(
            duration: Long = 500,
            @DrawableRes IconFromId: Int = R.drawable.ic_star_border,
            @DrawableRes IconToId: Int = R.drawable.ic_star
    ): ValueAnimator {
        return ValueAnimator.ofInt(
                IconFromId,
                IconToId
        ).apply {
            addUpdateListener {
                setBackgroundResource(it.animatedValue as Int)
            }
            this.duration = duration
        }
    }

}
