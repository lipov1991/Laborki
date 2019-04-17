package pl.krzysztof.lipka.laborki

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import pl.krzysztof.lipka.laborki.di.DaggerAppComponent
import timber.log.Timber

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}
