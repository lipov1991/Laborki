package pl.krzysztof.lipka.laborki

import android.app.Application
import pl.krzysztof.lipka.laborki.di.AppComponent
import pl.krzysztof.lipka.laborki.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    private val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}
