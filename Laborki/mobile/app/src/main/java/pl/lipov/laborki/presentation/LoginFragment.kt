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
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import org.koin.android.ext.android.bind
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.FragmentLoginBinding
import java.time.Duration


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var loginCallback: LoginCallback? = null
    private var iconAnimator: ValueAnimator? = null
    private var iconAnimator2: ValueAnimator? = null
    private var iconAnimator3: ValueAnimator? = null
    private var iconAnimator4: ValueAnimator? = null
    private val connector: ConnectFragment by activityViewModels()
    private var seq: Int = 1
    lateinit var ACTIVITY: MainActivity

    private val screenUnlockKey =
        listOf(
            Event.DOUBLE_TAP,
            Event.DOUBLE_TAP,
            Event.LONG_CLICK,
            Event.ACCELERATION_CHANGE
        )

    private val userGesturePass = mutableListOf<Event>()
    private var loginNo = 0

    fun listsEqual(list1: List<Any>, list2: List<Any>): Boolean {
        if (list1.size != list2.size)
            return false
        val pairList = list1.zip(list2)
        return pairList.all { (elt1, elt2) ->
            elt1 == elt2
        }
    }


    fun addGestureToList(event : Event){
        userGesturePass.add(event)

        if(userGesturePass.size==screenUnlockKey.size){
            if(listsEqual(userGesturePass,screenUnlockKey)){
                loginCallback?.onLoginSuccess()
                loginNo = 0
            }else{
                loginNo += 1
            }
            userGesturePass.clear()
        }
        if(loginNo == 3){
            loginNo = 0
        }
    }


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


        connector.getEvent.observe(viewLifecycleOwner, Observer {

            if (userGesturePass.size == 0){
                binding.icFire.visibility = View.VISIBLE
                iconAnimator?.start()
            }
            else if (userGesturePass.size == 1){
                binding.icFire2.visibility = View.VISIBLE
                iconAnimator2?.start()
            }
            else if (userGesturePass.size == 2){
                binding.icFire3.visibility = View.VISIBLE
                iconAnimator3?.start()
            }
            else if (userGesturePass.size == 3){
                binding.icFire4.visibility = View.VISIBLE
                iconAnimator4?.start()
            }
            else if (userGesturePass.size == 4) {
                iconAnimator?.end()
                iconAnimator2?.cancel()
                iconAnimator3?.cancel()
                iconAnimator4?.end()
            }
            addGestureToList(it)

        })



        return binding.root
    }



    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.isEnabled = false


        iconAnimator = binding.icFire
            .getTintAnimator()
            .apply {
                doOnEnd {
                    iconAnimator?.start()
                }
            }

        iconAnimator2 = binding.icFire2
            .getTintAnimator()
            .apply {
                doOnStart {
                    iconAnimator?.start()
                }
                doOnEnd {
                    iconAnimator2?.start()
                }
            }

        iconAnimator3 = binding.icFire3
            .getTintAnimator()
            .apply {
                doOnStart {
                    iconAnimator2?.start()
                }
                doOnEnd {
                    iconAnimator3?.start()
                }
            }

        iconAnimator4 = binding.icFire4
            .getTintAnimator()
            .apply {
                doOnStart {
                    iconAnimator3?.start()
                }
                doOnEnd {
                    iconAnimator4?.start()

                }
            }


        binding.startLoginButton.setOnClickListener {
            binding.icFire.visibility = View.INVISIBLE
            binding.icFire2.visibility = View.INVISIBLE
            binding.icFire3.visibility = View.INVISIBLE
            binding.icFire4.visibility = View.INVISIBLE

            binding.loginButton.isEnabled = true
            Toast.makeText(context, "Wykonaj 4 gesty", Toast.LENGTH_SHORT).show()

        }

        binding.loginButton.setOnClickListener {
            loginCallback?.onLoginSuccess()

        }
        binding.loginButton.setOnClickListener {
            if(listsEqual(userGesturePass,screenUnlockKey)) {
                loginCallback?.onLoginSuccess()
            }
            else{
                loginCallback?.onLoginFail()
                binding.loginButton.isEnabled = false
            }

        }


    }
    private fun View.getTintAnimator(
        duration: Long = 500,
        @ColorRes firstColorResId: Int = R.color.army,
        @ColorRes secondColorResId: Int = R.color.fire
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
