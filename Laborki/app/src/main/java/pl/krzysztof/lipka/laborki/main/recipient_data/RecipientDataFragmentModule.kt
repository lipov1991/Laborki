package pl.krzysztof.lipka.laborki.main.recipient_data

import dagger.Binds
import dagger.Module

@Module
abstract class RecipientDataFragmentModule {

    @Binds
    abstract fun bindsRecipientDataView(
        recipientDataFragment: RecipientDataFragment
    ): RecipientDataView
}
