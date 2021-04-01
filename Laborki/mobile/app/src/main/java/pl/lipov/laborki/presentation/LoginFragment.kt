package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentLoginBinding
import pl.lipov.laborki.presentation.MainViewModel
import pl.lipov.laborki.data.model.Event

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var attemptContainer = mutableListOf<Event>()
    private var incorrectAttempts: Int = 0
    private val svm: MainViewModel by activityViewModels()

    private val screenUnlockKey =
        listOf(
            Event.DOUBLE_TAP,
            Event.DOUBLE_TAP,
            Event.LONG_CLICK,
            Event.ACCELERATION_CHANGE
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        svm.attemptEnterPassword.observe(viewLifecycleOwner, Observer {
            attemptContainer.add(it)

            if (attemptContainer.size == 1) {
                binding.icLoginAttempt.getBackgroundAnimator().start()
                binding.icLoginAttempt.getBackgroundAnimator(
                    1000,
                    R.color.colorPrimary,
                    R.color.grey
                ).start()
            }
            if (attemptContainer.size == 2) {
                binding.icLoginAttempt.getBackgroundAnimator().start()
                binding.icLoginAttempt.getBackgroundAnimator(
                    1000,
                    R.color.colorPrimary,
                    R.color.grey
                ).start()
            }
            if (attemptContainer.size == 3) {
                binding.icLoginAttempt.getBackgroundAnimator().start()
                binding.icLoginAttempt.getBackgroundAnimator(
                    1000,
                    R.color.colorPrimary,
                    R.color.grey
                ).start()
            }
            if (attemptContainer.size == 4) {
                binding.icLoginAttempt.getBackgroundAnimator().start()

                if (attemptContainer == screenUnlockKey) {

                    binding.apply {
                        icLoginAttempt.getBackgroundAnimator(
                            firstColorResId = R.color.grey,
                            secondColorResId = R.color.green
                        ).start()
                    }

                    Toast.makeText(context, "Zalogowano", Toast.LENGTH_LONG).show()
                    svm.attemptEnterPassword.removeObservers(viewLifecycleOwner)

                } else {
                    incorrectAttempts += 1
                    Toast.makeText(
                        context, "Logowanie nie powiodło się. Pozostało: " +
                                "${3 - incorrectAttempts}" + " prób", Toast.LENGTH_LONG
                    ).show()

                    binding.apply {
                        icLoginAttempt.getBackgroundAnimator(
                            firstColorResId = R.color.grey,
                            secondColorResId = R.color.error
                        ).start()
                    }
                }
                attemptContainer.clear()
            }

            if (incorrectAttempts == 3) {
                Toast.makeText(
                    context,
                    "Urządzenie zostało zablokowane z powodu przekroczenia liczby prób",
                    Toast.LENGTH_LONG
                ).show()
                svm.attemptEnterPassword.removeObservers(viewLifecycleOwner)
            }
        })

    }

    private fun View.getBackgroundAnimator(
        duration: Long = 500,
        @ColorRes firstColorResId: Int = R.color.grey,
        @ColorRes secondColorResId: Int = R.color.colorPrimary
    ): ValueAnimator {
        return ValueAnimator.ofArgb(
            getColor(context, firstColorResId),
            getColor(context, secondColorResId)
        ).apply {
            addUpdateListener {
                setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }
}