package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.FragmentLoginBinding

class LoginFragment :  Fragment() {

    private val sharedVM: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    private var attemptPlaceHolder = mutableListOf<Event>()
    private var incorrectLoginAttempts: Int = 0

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
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        sharedVM.attemptEnterPassword.observe(viewLifecycleOwner, Observer {
            attemptPlaceHolder.add(it)

            if (attemptPlaceHolder.size == 1) {binding.icFirstLogin.setIconAnimator().start()}
            if (attemptPlaceHolder.size == 2) {binding.icSecondLogin.setIconAnimator().start()}
            if (attemptPlaceHolder.size == 3) {binding.icThirdLogin.setIconAnimator().start()}

            if (attemptPlaceHolder.size == 4) {
                binding.icFourthLogin.setIconAnimator().start()

                if (attemptPlaceHolder == screenUnlockKey) {

                    binding.apply {
                        icFirstLogin.getTintAnimator().start()
                        icSecondLogin.getTintAnimator(1000).start()
                        icThirdLogin.getTintAnimator(1500).start()
                        icFourthLogin.getTintAnimator(2000).start()
                    }

                    Toast.makeText(context, "Zostałeś zalogowany", Toast.LENGTH_LONG).show()
                    sharedVM.attemptEnterPassword.removeObservers(viewLifecycleOwner)

                } else {
                    incorrectLoginAttempts +=1
                    Toast.makeText(context, "Nieudana próba logowania. Pozostała liczba prób: " +
                            "${3 - incorrectLoginAttempts}", Toast.LENGTH_LONG).show()

                    binding.apply {
                        icFirstLogin.setIconAnimator(IconFromId = R.drawable.ic_star,
                                IconToId = R.drawable.ic_star_border).start()
                        icSecondLogin.setIconAnimator(IconFromId = R.drawable.ic_star,
                                IconToId = R.drawable.ic_star_border).start()
                        icThirdLogin.setIconAnimator(IconFromId = R.drawable.ic_star,
                                IconToId = R.drawable.ic_star_border).start()
                        icFourthLogin.setIconAnimator(IconFromId = R.drawable.ic_star,
                                IconToId = R.drawable.ic_star_border).start()
                    }
                }
                attemptPlaceHolder.clear()
            }

            if (incorrectLoginAttempts == 3) {
                Toast.makeText(context, "Zostałeś zablokowany", Toast.LENGTH_LONG).show()
                sharedVM.attemptEnterPassword.removeObservers(viewLifecycleOwner)
            }
        })

    }
//    Przed nazwą funkcji wskazany typ klasy która rozszerzamy
    private fun View.getTintAnimator(
        duration: Long = 500,
        @ColorRes ColorFromId: Int = R.color.army,
        @ColorRes ColorToId: Int = R.color.gold
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