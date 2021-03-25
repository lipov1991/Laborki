package pl.lipov.laborki.presentation

import android.animation.ValueAnimator
import android.content.Context
import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.drawable.DrawableCompat.setTint
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
//import kotlinx.android.synthetic.main.activity_training.*
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Event
import pl.lipov.laborki.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var starAnimator: ValueAnimator? = null
    private var emotion: ValueAnimator? = null
    private var emotion2: ValueAnimator? = null
    private var emotion3: ValueAnimator? = null
    private var emotion4: ValueAnimator? = null
    private val viewModel: MainViewModel by activityViewModels()
    private var loginCallback: LoginCallback? = null

    private val screenUnlockKey = listOf(
            Event.LONG_CLICK,
            Event.DOUBLE_TAP,
            Event.LONG_CLICK,
            Event.ACCELERATION_CHANGE
    )

    private var lista = mutableListOf<Event>()
    private var liczbaProb = 0

    fun listsEqual(list1: List<Any>, list2: List<Any>): Boolean {
        if (list1.size != list2.size)
            return false
        val pairList = list1.zip(list2)
        return pairList.all { (elt1, elt2) ->
            elt1 == elt2
        }
    }

    fun addElement(event : Event){
        lista.add(event)
        if(lista.size==screenUnlockKey.size){
            if(listsEqual(lista,screenUnlockKey)){
                loginCallback?.onLoginSuccess()
                liczbaProb = 0
            }else{
                loginCallback?.onLoginUnSuccess()
                liczbaProb = liczbaProb +1
            }
            lista.clear()
        }
        if(liczbaProb == 3){
            loginCallback?.Block()
            liczbaProb = 0
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginCallback){
            loginCallback = context
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            //loginCallback?.onLoginSuccess()
            if (lista.size == 0){
                emotion?.start()
            }
            else if (lista.size == 1){
                emotion2?.start()
            }
            else if (lista.size == 2){
                emotion3?.start()
            }
            else if (lista.size == 3){
                emotion4?.start()
            }
            addElement(it)
        })

        return binding.root
    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        emotion = binding.icEmotions
                .getTintAnimator()
                .apply {
                    doOnEnd{
                        starAnimator?.start()
                    }
                }

        emotion2 = binding.icEmotions2
            .getTintAnimator()
            .apply {
                doOnEnd{
                    starAnimator?.start()
                }
            }

        emotion3 = binding.icEmotions3
            .getTintAnimator()
            .apply {
                doOnEnd{
                    starAnimator?.start()
                }
            }

        emotion4 = binding.icEmotions4
            .getTintAnimator()
            .apply {
                doOnEnd{
                    starAnimator?.start()
                }
            }
    }

    private fun View.getTintAnimator(       //funkcja, ktora umozliwia zrobienie takiej samej animacji, ale z innymi parametrami
                                            //jest to osobny kod, poniewaz kod sie powtarza, zawsze w sytuacji powtorzen tworzymy nowa funkcje
                //View - typ klasy, ktora rozszerzamy poprzez ContextCompat
            duration: Long = 1500,               //pierwsza animacja nie ma wartosci czasu trwania, nadajemy ja tutaj
            //druga natomiast ma, dlatego bedzie trwac dluzej
            @ColorRes firstColorResId: Int = R.color.yellow,
            @ColorRes secondColorResId: Int = R.color.grey
    ): ValueAnimator {          //funkcja zwroci ValueAnimator, ale w miedzy czasie ja przetworzy, skonfiguruje animacje
        return ValueAnimator.ofArgb(            //funkcja ofArgb - funkcja statyczna, mozna sie do niej dostac bez tworzenia jej klasy
                getColor(context, firstColorResId),       //context - kontekst widoku
                getColor(context, secondColorResId)
        ).apply {
            addUpdateListener {
                setTint(background, it.animatedValue as Int)
            }
            this.duration = duration
        }
    }
}