package pl.lipov.laborki.presentation.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_first.*
import pl.lipov.laborki.R
import pl.lipov.laborki.common.setBackground

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_first, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        text_view.setOnClickListener {
            (activity as? TrainingActivity)?.showFragment(SecondFragment())
        }
        edit_text.doOnTextChanged { text, start, count, after ->
            if (text.isNullOrEmpty()) {
                edit_text.error = "To pole nie może być puste"
                edit_text.setBackground(R.drawable.edit_text_error_background)
            } else {
                edit_text.error = null
                edit_text.setBackground(R.drawable.edit_text_background)
            }
        }
    }
}
