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


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var loginCallback: LoginCallback? = null
    private var iconAnimator: ValueAnimator? = null
    private var iconAnimator2: ValueAnimator? = null
    private var iconAnimator3: ValueAnimator? = null
    private var iconAnimator4: ValueAnimator? = null

    private var borderediconAnimator: ValueAnimator? = null

    private val connector: ConnectFragment by activityViewModels()


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

    private val userGesturePass = mutableListOf<Event>() // mutable zapewnia to ze mozna dodawac i usuwac z listy
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
                binding.icUnlock.visibility = View.VISIBLE
                loginNo = 0
            }else{
                //loginCallback?.onTest_nieUdalo() zaimplementowac
                loginNo += 1
            }
            userGesturePass.clear()
        }
        if(loginNo == 3){
            //loginCallback?.onLoginLock()
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


//        connector.getEvent.observe(viewLifecycleOwner, Observer {
//
//        }

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
                binding.icUnlock.visibility = View.VISIBLE
            }
            addGestureToList(it)
            //borderediconAnimator?.start()
        })
        //return inflater.inflate(R.layout.fragment_login,container, false)

        return binding.root
    }


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        //binding.loginButton.isEnabled = true
        binding.loginButton.isEnabled = false

        //if (OnDoubleTapFlag == true and OnLongPressFlag == true) {
        iconAnimator = binding.icFire
                .getTintAnimator() //duration = 1000
                .apply {
                    doOnEnd {
                        iconAnimator?.start() //dodac?
                        //binding.loginButton.isEnabled = true
                    }
                }

        iconAnimator2 = binding.icFire2
            .getTintAnimator() //duration = 1000
            .apply {
                doOnStart {
                    iconAnimator?.start()
                }
                doOnEnd {
                    iconAnimator2?.start() //dodac?
                    //binding.loginButton.isEnabled = true
                }
            }

        iconAnimator3 = binding.icFire3
            .getTintAnimator() //duration = 1000
            .apply {
                doOnStart {
                    iconAnimator2?.start()
                }
                doOnEnd {
                    iconAnimator3?.start() //dodac?
                    //binding.loginButton.isEnabled = true
                }
            }

        iconAnimator4 = binding.icFire4
            .getTintAnimator() //duration = 1000
            .apply {
                doOnStart {
                    iconAnimator3?.start()
                }
                doOnEnd {
                   iconAnimator4?.start() //dodac?
                   // binding.loginButton.isEnabled = true
                }
            }
       // }

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
            //borderediconAnimator?.start()
            if(listsEqual(userGesturePass,screenUnlockKey)) {
                loginCallback?.onLoginSuccess()}
            else{
                loginCallback?.onLoginFail()
                binding.loginButton.isEnabled = false
            }



//            if (OnDoubleTapFlag == "D" || OnLongPressFlag == "L") {
//                //binding.icBorderedStar.visibility = View.VISIBLE
//                borderediconAnimator?.start()
//                if (OnDoubleTapFlag == "D") {seq += 1}
//                    if (OnDoubleTapFlag == "L") {seq += 1}
//            }

//                if (passFlagFragment == "DDLA") {
//                    iconAnimator?.start()
//                    binding.icStar.visibility = View.VISIBLE
//                }

            }


    }
    private fun View.getTintAnimator(
        duration: Long = 1000,
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