package pl.krzysztof.lipka.laborki.main

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.krzysztof.lipka.laborki.main.recipient_data.RecipientDataFragment
import pl.krzysztof.lipka.laborki.main.recipient_data.RecipientDataFragmentModule

@Module
abstract class MainActivityModule {

    @Binds
    abstract fun bindsMainView(
        mainActivity: MainActivity
    ): MainView

    @ContributesAndroidInjector(modules = [RecipientDataFragmentModule::class])
    abstract fun contributesRecipientDataFragmentInjector(): RecipientDataFragment
}
