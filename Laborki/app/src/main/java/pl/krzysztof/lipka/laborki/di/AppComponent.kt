package pl.krzysztof.lipka.laborki.di

import dagger.Component
import dagger.android.AndroidInjector
import pl.krzysztof.lipka.laborki.App
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
