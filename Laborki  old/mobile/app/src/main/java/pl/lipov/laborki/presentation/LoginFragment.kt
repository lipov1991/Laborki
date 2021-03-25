package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Bundle
import android.util.EventLog
import android.view.*
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.bind
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var loginCallback: LoginCallback? = null

    private var starAnimator: ValueAnimator? = null
    private var borderedStarAnimator: ValueAnimator? = null


    //(activity as MainActivity).te

    private var seq: Int = 1
    lateinit var ACTIVITY: MainActivity

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

        ACTIVITY = context as MainActivity


        if (context is LoginCallback){
            loginCallback = context
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        //return inflater.inflate(R.layout.fragment_login,container, false)



        return binding.root
    }



    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        var counter: Int = 0

        binding.loginButton.isEnabled = false
        borderedStarAnimator = binding.icBorderedStar
            .getTintAnimator()
            .apply {
                doOnStart {
                    binding.loginButton.isEnabled = false

                }
                doOnEnd {
                    //EventLog == Event.DOUBLE_TAP
                    //starAnimator?.start()
                    //if (OnDoubleTapFlag == true and OnLongPressFlag) {
                        //starAnimator?.start()
                    //}
                        binding.loginButton.isEnabled = true
                        //binding.icStar.visibility = View.VISIBLE
                        //binding.loginButton.isEnabled = true


                        if (counter == 4) {
                            binding.loginButton.isEnabled = false
                            Toast.makeText(
                                context,
                                "3 nieudane próby logowania!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        counter = counter?.plus(1)
                    }
                }


        //if (OnDoubleTapFlag == true and OnLongPressFlag == true) {
        starAnimator = binding.icStar
                .getTintAnimator() //duration = 1000
                .apply {
                    doOnEnd {
                        starAnimator?.start() //dodac?
                        binding.loginButton.isEnabled = true
                    }
                }
       // }

        binding.startLoginButton.setOnClickListener {
            binding.icBorderedStar.visibility = View.VISIBLE
            binding.loginButton.isEnabled = true
            Toast.makeText(context, "Wykonaj 4 gesty", Toast.LENGTH_SHORT).show()

        }

        binding.loginButton.setOnClickListener {
            loginCallback?.onLoginSuccess()

        }
        binding.loginButton.setOnClickListener {
            //borderedStarAnimator?.start()


                var passFlagFragment = ACTIVITY.passwordFlag

                println(passFlagFragment)


//            if (OnDoubleTapFlag == "D" || OnLongPressFlag == "L") {
//                //binding.icBorderedStar.visibility = View.VISIBLE
//                borderedStarAnimator?.start()
//                if (OnDoubleTapFlag == "D") {seq += 1}
//                    if (OnDoubleTapFlag == "L") {seq += 1}
//            }

                if (passFlagFragment == "DDLA") {
                    starAnimator?.start()
                    binding.icStar.visibility = View.VISIBLE
                }
                //var wykrywanie = ACTIVITY.wykryjDoubleTap
                //binding.icStar.visibility = View.VISIBLE
                // nasłuchuj poprawnej sekwencji, zwieksz licznik o 1, 3x - błąd

            }


    }
    private fun View.getTintAnimator(
        duration: Long = 500,
        @ColorRes firstColorResId: Int = R.color.army,
        @ColorRes secondColorResId: Int = R.color.grey
    ): ValueAnimator{
        return ValueAnimator.ofArgb(
            getColor(context, firstColorResId),
            getColor(context,secondColorResId)
        ).apply {
            addUpdateListener {
                setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }

}