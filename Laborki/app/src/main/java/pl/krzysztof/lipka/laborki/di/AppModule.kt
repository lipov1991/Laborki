package pl.krzysztof.lipka.laborki.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import pl.krzysztof.lipka.laborki.App
import pl.krzysztof.lipka.laborki.common.utils.alert_dialog.AlertDialogUtils
import pl.krzysztof.lipka.laborki.common.utils.alert_dialog.AlertDialogUtilsImpl
import pl.krzysztof.lipka.laborki.data.CoordinatesRepository
import pl.krzysztof.lipka.laborki.data.CoordinatesRepositoryImpl

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

    @Binds
    abstract fun bindsAlertDialogUtils(
        alertDialogUtilsImpl: AlertDialogUtilsImpl
    ): AlertDialogUtils

    @Binds
    abstract fun bindsCoordinatesRepository(
        coordinatesRepositoryImpl: CoordinatesRepositoryImpl
    ): CoordinatesRepository
}
