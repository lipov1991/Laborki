package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.FragmentLoginBinding
import pl.lipov.laborki.presentation.login.LoginCallback

class LoginFragment : Fragment() {

    private val sharedVM: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding
    private var loginCallback: LoginCallback? = null
    private var eventHolder = mutableListOf<Event>()
    private var failedAttempts: Int = 0
    private val unlockKey =
        listOf<Event>(Event.LONG_CLICK, Event.DOUBLE_TAP, Event.DOUBLE_TAP, Event.ACCELERATION_CHANGE)

    override fun onAttach(
        context: Context
    ) {
        super.onAttach(context)
        if (context is LoginCallback) {
            loginCallback = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)
        sharedVM.passwordAuthentication.observe(viewLifecycleOwner, Observer {
            eventHolder.add(it)

            if (eventHolder.size == 1) {
                binding.apply { icStar1.getTintAnimator(200, R.color.army, R.color.blue).start() }
            }
            if (eventHolder.size == 2) {
                binding.apply { icStar2.getTintAnimator(200, R.color.army, R.color.blue).start() }
            }
            if (eventHolder.size == 3) {
                binding.apply { icStar3.getTintAnimator(200, R.color.army, R.color.blue).start() }
            }
            if (eventHolder.size == 4) {
                binding.apply { icStar4.getTintAnimator(200, R.color.army, R.color.blue).start() }
            }

            if (eventHolder.size == 4) {
                if (eventHolder == unlockKey) {
                    binding.apply {
                        icStar1.getTintAnimator(1000 / 2, R.color.blue, R.color.green).start()
                        icStar2.getTintAnimator(3000 / 2, R.color.blue, R.color.green).start()
                        icStar3.getTintAnimator(5000 / 2, R.color.blue, R.color.green).start()
                        icStar4.getTintAnimator(7000 / 2, R.color.blue, R.color.green).start()
                        loginCallback?.onLoginSuccess()
                    }

                    Toast.makeText(context, "Zalogowano", Toast.LENGTH_LONG).show()

                } else {
                    failedAttempts += 1
                    binding.apply {
                        icStar4.getTintAnimator(300, R.color.blue, R.color.army).start()
                        icStar3.getTintAnimator(500, R.color.blue, R.color.army).start()
                        icStar2.getTintAnimator(700, R.color.blue, R.color.army).start()
                        icStar1.getTintAnimator(900, R.color.blue, R.color.army).start()
                    }
                    Toast.makeText(context, "Hasło niepoprawne", Toast.LENGTH_LONG).show()
                }
                eventHolder.clear()
            }

            if (failedAttempts == 3) {
                Toast.makeText(context, "3 nieudane próby", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun View.getTintAnimator(
        duration: Long = 2000,
        @ColorRes ColorFromId: Int = R.color.army,
        @ColorRes ColorToId: Int = R.color.error
    ): ValueAnimator {
        return ValueAnimator.ofArgb(
            getColor(context, ColorFromId),
            getColor(context, ColorToId)
        ).apply {
            addUpdateListener {
                setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }
}