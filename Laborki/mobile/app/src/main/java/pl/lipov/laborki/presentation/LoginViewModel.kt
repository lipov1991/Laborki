package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.data.repository.LoginRepository

class LoginViewModel(

    val loginRepository: LoginRepository,
    private var loginCallback: LoginCallback? = null,

) : ViewModel() {

    private var attemptContainer = mutableListOf<Event>()
    private var incorrectAttempts: Int = 0
    val onEvent: MutableLiveData<Event> = loginRepository.attemptEnterPassword

    private val screenUnlockKey: List<Event> =
        listOf(
            Event.valueOf(loginRepository.screenUnlockKey.event1),
            Event.valueOf(loginRepository.screenUnlockKey.event2),
            Event.valueOf(loginRepository.screenUnlockKey.event3),
            Event.valueOf(loginRepository.screenUnlockKey.event4)
        )

    fun gestureCheck(
    icon: View,
    event: Event
    ) {

            attemptContainer.add(event)

            if (attemptContainer.size == 1) {
                icon.getBackgroundAnimator().start()
                icon.getBackgroundAnimator(
                    1000,
                    R.color.colorPrimary,
                    R.color.grey
                ).start()
            }
            if (attemptContainer.size == 2) {
                icon.getBackgroundAnimator().start()
                icon.getBackgroundAnimator(
                    1000,
                    R.color.colorPrimary,
                    R.color.grey
                ).start()
            }
            if (attemptContainer.size == 3) {
                icon.getBackgroundAnimator().start()
                icon.getBackgroundAnimator(
                    1000,
                    R.color.colorPrimary,
                    R.color.grey
                ).start()
            }
            if (attemptContainer.size == 4) {
                icon.getBackgroundAnimator().start()

                if (attemptContainer == screenUnlockKey) {

                    apply {
                        icon.getBackgroundAnimator(
                            firstColorResId = R.color.grey,
                            secondColorResId = R.color.green
                        ).start()
                    }

                    loginCallback?.onPasswordSuccess()

                } else {
                    incorrectAttempts += 1
                    loginCallback?.onPasswordError()

                    apply {
                        icon.getBackgroundAnimator(
                            firstColorResId = R.color.grey,
                            secondColorResId = R.color.error
                        ).start()
                    }
                }
                attemptContainer.clear()
            }

            if (incorrectAttempts == 3) {
                loginCallback?.onAttemptLimit()
            }

    }

    private fun View.getBackgroundAnimator(
        duration: Long = 500,
        @ColorRes firstColorResId: Int = R.color.grey,
        @ColorRes secondColorResId: Int = R.color.colorPrimary
    ): ValueAnimator {
        return ValueAnimator.ofArgb(
            ContextCompat.getColor(context, firstColorResId),
            ContextCompat.getColor(context, secondColorResId)
        ).apply {
            addUpdateListener {
                DrawableCompat.setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }
}