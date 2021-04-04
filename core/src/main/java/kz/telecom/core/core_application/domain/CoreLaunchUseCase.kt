package kz.telecom.core.core_application.domain

import kz.telecom.core.kd_dispatcher.IKDispatcher
import kz.telecom.core.core_application.utils.delegates.CoreCoroutine
import kz.telecom.core.core_application.utils.delegates.CoreCoroutineDelegate

/**
 * В случае если нужно отловить запрос в UseCase и преобразовать данный необходимо воспользоваться данным классом
 * @param T параметр метода
 * @param V результат выполненного действия
 */
abstract class CoreLaunchUseCase<in T, out V> : CoreCoroutine by CoreCoroutineDelegate(),
    IKDispatcher {

    abstract fun execute(param: T, result: ((V) -> Unit))
}


/**
 * В случае если нужно отловить запрос в UseCase и преобразовать данный необходимо воспользоваться данным классом
 * @param V результат выполненного действия
 */
abstract class CoreNonParamLaunchUseCase<out V> : CoreCoroutine by CoreCoroutineDelegate(),
    IKDispatcher {

    abstract fun execute(result: ((V) -> Unit))
}

