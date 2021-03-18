package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.FragmentLoginBinding

class LoginFragment :  Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var LoginCallback: LoginCallback? = null
    private var starAnimator: ValueAnimator? = null
    private var borderedStarAnimator: ValueAnimator? = null

    private val screenUnlockKey =
            listOf(
                    Event.DOUBLE_TAP,
                    Event.DOUBLE_TAP,
                    Event.LONG_CLICK,
                    Event.ACCELERATION_CHANGE
            )

    override fun onAttach(
        context: Context
    ) {
        super.onAttach(context)
        if (context is LoginCallback){
            LoginCallback = context
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        borderedStarAnimator = binding.icBorderedStar
                .getTintAnimator()
                .apply {
                    doOnStart {
                       binding.loginButton.isEnabled = false
                    }
                    doOnEnd {
                        starAnimator?.start()
                    }
                }

        starAnimator = binding.icStar
                .getTintAnimator(duration = 1000)
                .apply {
                    doOnEnd {
                        binding.loginButton.isEnabled = true
                    }
                }

        binding.loginButton.setOnClickListener{
            borderedStarAnimator?.start()
        }
    }
//    Przed nazwą funkcji wskazany typ klasy która rozszerzamy
    private fun View.getTintAnimator(
        duration: Long = 500,
        @ColorRes firstColorResId: Int = R.color.army,
        @ColorRes secondColorResId: Int = R.color.grey
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