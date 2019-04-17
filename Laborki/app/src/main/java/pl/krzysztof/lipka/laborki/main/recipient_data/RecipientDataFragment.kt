package pl.krzysztof.lipka.laborki.main.recipient_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.krzysztof.lipka.laborki.R
import pl.krzysztof.lipka.laborki.common.BaseFragment

class RecipientDataFragment : BaseFragment(), RecipientDataView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_recipient_data, container, false)
}
