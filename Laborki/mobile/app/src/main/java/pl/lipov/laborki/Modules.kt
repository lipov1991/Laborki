package pl.lipov.laborki

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.lipov.laborki.common.utils.GestureDetectorUtils
import pl.lipov.laborki.common.utils.SensorEventsUtils
import pl.lipov.laborki.data.LoginApi
import pl.lipov.laborki.data.LoginRepository
import pl.lipov.laborki.presentation.MainViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val LOGIN_API_ENDPOINT = "http://apka.targislubne.pl/"

val utilsModule = module {
    single { GestureDetectorUtils() }
    single { SensorEventsUtils() }
}

val networkModule = module {
    factory { provideOkHttpClient() }
    single { provideGson() }
    single { provideRetrofit(okHttpClient = get(), gson = get()) }
    factory { provideLoginApi(get()) }
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

private fun provideLoginApi(
    retrofit: Retrofit
): LoginApi = retrofit.create(LoginApi::class.java)

val repositoriesModule = module {
    factory { LoginRepository(loginApi = get()) }
}

val viewModelsModule = module {
    viewModel {
        MainViewModel(
            gestureDetectorUtils = get(),
            sensorEventsUtils = get(),
            loginRepository = get()
        )
    }
}
