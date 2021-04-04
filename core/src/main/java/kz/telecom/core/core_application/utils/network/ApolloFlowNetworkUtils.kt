package kz.telecom.core.core_application.utils.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.coroutines.toDeferred
import kz.telecom.core.core_application.data.network.ResultApi
import kz.telecom.core.core_application.utils.exeption.ApolloSuccessException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf


/**
 * Функция для вызова стандартных GQL запросов с возможностью рабоать с Flow
 * @return ResultApi
 */
suspend fun <A : Operation.Data, B : Any, C : Operation.Variables> ApolloClient.apolloFlowSafeApiCall(
    call: suspend () -> Query<A, B, C>
): Flow<B?> = flow {
    val result = query(call.invoke()).toDeferred().await()
    val isNullError = result.errors?.isNullOrEmpty() ?: true
    if (!isNullError) {
        emit(throw ApolloSuccessException(result.errors?.get(0)?.message.orEmpty()))
        return@flow
    }
    emit(result.data)
}

