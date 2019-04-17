package pl.krzysztof.lipka.laborki.main.recipient_data

import pl.krzysztof.lipka.laborki.data.CoordinatesRepository
import javax.inject.Inject

class RecipientDataPresenter @Inject constructor(
    private val view: RecipientDataView,
    private val repository: CoordinatesRepository
) {

    fun saveRecipientEmail(
        recipientEmail: String
    ) {
        repository.recipientEmail = recipientEmail
        view.onEmailSaved(repository.recipientEmail!!)
    }
}
