package pl.lipov.laborki.presentation.login

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.FragmentLoginBinding
import pl.lipov.laborki.presentation.ConnectViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var loginCallback: LoginCallback? = null
    private var StarAnimator1: ValueAnimator? = null
    private var StarAnimator2: ValueAnimator? = null
    private var StarAnimator3: ValueAnimator? = null
    private var StarAnimator4: ValueAnimator? = null
    private val viewConnector: ConnectViewModel by activityViewModels()
    val POSITIVE_INFINITY: Float = 0.0f
    private var counter = 0
    private val screenUnlockKey =
        listOf(
            Event.DOUBLE_TAP,
            Event.DOUBLE_TAP,
            Event.LONG_CLICK,
            Event.ACCELERATION_CHANGE)
    private val userSeq = mutableListOf<Event>()

    fun listsEqual(list1: List<Any>, list2: List<Any>): Boolean {
        if (list1.size != list2.size)
            return false
        val pairList = list1.zip(list2)
        return pairList.all { (elt1, elt2) ->
            elt1 == elt2
        }
    }

    fun addEvent(event: Event){
        userSeq.add(event)
        if (userSeq.size == screenUnlockKey.size) {
            if(listsEqual(userSeq,screenUnlockKey)){
                loginCallback?.onLoginSuccess()
                println("Udało się")
                counter = 0
            }
            else{
                println("Nie udało się")
                counter++
            }
            userSeq.clear()
//            StarAnimator1?.cancel()
//            StarAnimator2?.cancel()
//            StarAnimator3?.cancel()
//            StarAnimator4?.cancel()
        }
        if(counter == 3){
            println("Zalogowano")
        }

    }


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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewConnector.getEvent.observe(viewLifecycleOwner, Observer {

            if(userSeq.size == 0){
                StarAnimator1?.start()
            }
            if(userSeq.size == 1){
                StarAnimator2?.start()
            }
            if(userSeq.size == 2){
                StarAnimator3?.start()
            }
            if(userSeq.size == 3){
                StarAnimator4?.start()
            }
            addEvent(it)
        })
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
       super.onViewCreated(view, savedInstanceState)

       binding.loginButton.setOnClickListener {
           loginCallback?.onLoginSuccess()
       }
        StarAnimator1 = binding.icStar1
            .getBackgroundAnimator()
            .apply {
                doOnStart {

                }
                doOnEnd {

                }
            }
        StarAnimator2 = binding.icStar2
            .getBackgroundAnimator()
            .apply {
                doOnStart {

                }
                doOnEnd {

                }
            }
        StarAnimator3 = binding.icStar3
            .getBackgroundAnimator()
            .apply {
                doOnStart {

                }
                doOnEnd {

                }
            }
        StarAnimator4 = binding.icStar4
            .getBackgroundAnimator()
            .apply {
                doOnStart {

                }
                doOnEnd {

                }
            }
    }
    private fun View.getBackgroundAnimator(
        duration: Long = 9999999,
        @ColorRes firstColorResId: Int = R.color.army,
        @ColorRes secondColorResId: Int = R.color.grey
    ): ValueAnimator {
        return ValueAnimator.ofArgb(
            ContextCompat.getColor(context, firstColorResId),
            ContextCompat.getColor(context, secondColorResId)
        ).apply {
            addUpdateListener {
                setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }
}