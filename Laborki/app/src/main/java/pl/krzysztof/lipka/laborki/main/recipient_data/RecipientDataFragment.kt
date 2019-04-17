package pl.krzysztof.lipka.laborki.main.recipient_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_recipient_data.*
import pl.krzysztof.lipka.laborki.R
import pl.krzysztof.lipka.laborki.common.BaseFragment
import javax.inject.Inject

class RecipientDataFragment : BaseFragment(), RecipientDataView {

    @Inject
    lateinit var presenter: RecipientDataPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_recipient_data, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        next_button.setOnClickListener {
            presenter.saveRecipientEmail(email_text.text.toString())
        }
    }

    override fun onEmailSaved(
        savedEmail: String
    ) {
        activity?.let {
            Toast.makeText(it, "Email $savedEmail został zapisany", Toast.LENGTH_LONG).show()
        }
    }
}
