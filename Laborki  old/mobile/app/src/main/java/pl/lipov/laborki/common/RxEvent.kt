package pl.lipov.laborki.common

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class RxEvent<T> {

    private var errorRelay: PublishRelay<Throwable> = PublishRelay.create()
    private var successRelay: PublishRelay<T> = PublishRelay.create()

    fun getSuccessStream(): Flowable<T> = successRelay.toFlowable(BackpressureStrategy.LATEST)

    fun getErrorStream(): Flowable<Throwable> = errorRelay.toFlowable(BackpressureStrategy.LATEST)

    fun onSuccess(
        t: T
    ) {
        successRelay.accept(t)
    }

    fun onError(
        throwable: Throwable
    ) {
        errorRelay.accept(throwable)
    }
}
