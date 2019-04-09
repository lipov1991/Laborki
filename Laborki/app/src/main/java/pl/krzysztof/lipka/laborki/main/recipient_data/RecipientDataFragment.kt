package pl.krzysztof.lipka.laborki.main.recipient_data

import pl.krzysztof.lipka.laborki.R
import pl.krzysztof.lipka.laborki.common.BaseFragment

class RecipientDataFragment : BaseFragment<RecipientDataView, RecipientDataPresenter>(
    R.layout.fragment_recipient_data
), RecipientDataView {

    override val viewPresenter = RecipientDataPresenter()

    override fun createPresenter() = RecipientDataPresenter()
}
