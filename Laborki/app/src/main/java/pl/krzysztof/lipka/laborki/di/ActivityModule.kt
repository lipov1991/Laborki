package pl.krzysztof.lipka.laborki.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pl.krzysztof.lipka.laborki.main.MainActivity
import pl.krzysztof.lipka.laborki.main.MainActivityModule

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributesMainActivity(): MainActivity
}
