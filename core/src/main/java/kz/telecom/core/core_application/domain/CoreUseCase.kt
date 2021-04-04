package kz.telecom.core.core_application.domain

import kz.telecom.core.core_application.data.network.ResultApi
import kotlinx.coroutines.flow.Flow

/**
 * Интерфейс для реализации useCase с входящем параметром
 * @param T параметр метода
 * @param V результат выполненного действия
 */
interface CoreUseCase<in I, out V : Any> {

    suspend fun execute(param: I): ResultApi<V>
}


/**
 * Интерфейс для реализации useCase без входящего параметром
 * @param V результат выполненного действия
 */
interface CoreNonParamUseCase<out V : Any> {

    suspend fun execute(): ResultApi<V>
}


/**
 * Интерфейс для реализации useCase (flow) с входящем параметром
 * @param T параметр метода
 * @param V результат выполненного действия
 */
interface CoreFlowUseCase<in I, out V : Any> {

    suspend fun execute(param: I): Flow<V>
}


/**
 * Интерфейс для реализации useCase (flow) без входящего параметром
 * @param T параметр метода
 * @param V результат выполненного действия
 */
interface CoreFlowNonParamUseCase<out V : Any> {

    suspend fun execute(): Flow<V>
}