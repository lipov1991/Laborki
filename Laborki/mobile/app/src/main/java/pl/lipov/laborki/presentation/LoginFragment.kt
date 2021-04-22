package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.LoginStatus
import pl.lipov.laborki.databinding.FragmentLoginBinding
import pl.lipov.laborki.presentation.map.MapActivity

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding


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

        val starIdList =
            listOf(
                binding.icFirstLogin,
                binding.icSecondLogin,
                binding.icThirdLogin,
                binding.icFourthLogin
            )

        viewModel.run {
            onEvent.observe(viewLifecycleOwner, Observer {
                addCurrentSample(it)
                passwordInput().apply {
                    starIdList[this].setIconAnimator().start()
                }

                passwordCheck()?.apply {
                    if (this == LoginStatus.SUCCESS) {
                        Toast.makeText(context, this.value, Toast.LENGTH_LONG).show()
                        starIdList.forEachIndexed { index, element ->
                            element.getTintAnimator(duration = ((index + 1) * 500).toLong()).start()
                        }
                        activity?.let { parentActivity ->
                            val intent = Intent(parentActivity, MapActivity::class.java)
                            startActivity(intent)
                        }
                    } else if (this == LoginStatus.UNSUCCESS) {
                        Toast.makeText(
                            context,
                            this.value + "${3 - incorrectLoginAttempts}",
                            Toast.LENGTH_LONG
                        ).show()
                        starIdList.forEach {
                            it.setIconAnimator(
                                IconFromId = R.drawable.ic_star,
                                IconToId = R.drawable.ic_star_border
                            ).start()
                        }
                    } else {
                        Toast.makeText(context, this.value, Toast.LENGTH_LONG).show()
                        onEvent.removeObservers(viewLifecycleOwner)
                    }
                }
            })
        }
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