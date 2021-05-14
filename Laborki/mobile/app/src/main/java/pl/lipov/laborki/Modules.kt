package pl.lipov.laborki

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.lipov.laborki.common.utils.*
import pl.lipov.laborki.data.repository.LoginRepository
import pl.lipov.laborki.data.repository.api.Api
import pl.lipov.laborki.presentation.AuthorizationViewModel
import pl.lipov.laborki.presentation.LoginViewModel
import pl.lipov.laborki.presentation.MainViewModel
import pl.lipov.laborki.presentation.map.MapViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val LOGIN_API_ENDPOINT = "https://laborki-7e3b1.firebaseio.com/"

val utilsModule = module {
    single { STTUtils() }
    single { GestureDetectorUtils() }
    single { provideSensorManager(context = get()) }
    single(named("accelerometer")) {
        provideAccelerometer(sensorManager = get())
    }
    single(named("magnetometer")) {
        provideMagnetometer(sensorManager = get())
    }
    single {
        CompassUtils(
            accelerometer = get(named("accelerometer")),
            magnetometer = get(named("magnetometer")),
            sensorManager = get(),
        )
    }
    single { SensorEventsUtils(sensorManager = get(), accelerometer = get(named("accelerometer"))) }
    single { MapUtils() }
}

private fun provideSensorManager(
    context: Context
): SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

private fun provideAccelerometer(
    sensorManager: SensorManager
): Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

private fun provideMagnetometer(
    sensorManager: SensorManager
): Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

val networkModule = module {
    factory { provideOkHttpClient() }
    single { provideGson() }
    single { provideRetrofit(okHttpClient = get(), gson = get()) }
    factory { provideApi(get()) }
}

private fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder().build()

private fun provideGson(): Gson = GsonBuilder().serializeNulls().setLenient().create()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gson: Gson
): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(LOGIN_API_ENDPOINT)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

private fun provideApi(
    retrofit: Retrofit
): Api = retrofit.create(Api::class.java)

val repositoriesModule = module {
    single { LoginRepository(api = get()) }
}

val viewModelsModule = module {
    viewModel {
        MainViewModel(
            gestureDetectorUtils = get(),
            sensorEventsUtils = get(),
            loginRepository = get()
        )
    }

    viewModel {
        LoginViewModel(
            loginRepository = get()
        )
    }

    viewModel {
        AuthorizationViewModel(
            loginRepository = get()
        )
    }

    viewModel {
        MapViewModel(
            mapUtils = get(),
            loginRepository = get(),
            compassUtils = get()
        )
    }
}
