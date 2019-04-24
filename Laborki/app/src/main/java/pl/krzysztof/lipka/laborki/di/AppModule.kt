package pl.krzysztof.lipka.laborki.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import pl.krzysztof.lipka.laborki.App

@Module(includes = [AndroidSupportInjectionModule::class, ActivityModule::class])
abstract class AppModule {

    @Binds
    abstract fun bindsApplication(
        app: App
    ): Application

    @Binds
    abstract fun bindsContext(
        app: App
    ): Context
}
