package kz.dungeonmasters.core.core_application.utils.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.apollographql.apollo.exception.ApolloParseException
import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.utils.exeption.ApolloSuccessException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import javax.net.ssl.SSLHandshakeException

/**
 * Функция для вызова стандартных GQL запросов
 * @return ResultApi
 */
suspend fun <A : Operation.Data, B : Any, C : Operation.Variables> ApolloClient.apolloSafeApiCall(
    call: suspend () -> Query<A, B, C>
): ResultApi<B> {
    return try {
        val result = query(call.invoke()).toDeferred().await()
        val isNullError = result.errors?.isNullOrEmpty() ?: true
        if (!isNullError) {
            throw ApolloSuccessException(result.errors?.get(0)?.message.orEmpty())
        }
        ResultApi.Success(result.data)
    } catch (e: ApolloException) {
        handleApolloException(e)
    }
}

/**
 * Обработка ошибок. Обработка ошибок для flow [handleError]
 */
fun <T : Any> handleApolloException(e: ApolloException): ResultApi<T> {
    return when (e) {
        is ApolloHttpException -> when (e.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> ResultApi.HttpError(
                "Срок действия сессии истек",
                e.code()
            )
            HttpURLConnection.HTTP_NOT_FOUND -> ResultApi.HttpError(
                "Данного запроса не существует",
                e.code()
            )
            HttpURLConnection.HTTP_SERVER_ERROR -> ResultApi.HttpError(
                "Внутренняя ошибка сервера",
                e.code()
            )
            else -> ResultApi.HttpError("Ошибка сервера", e.code())
        }

        is ApolloNetworkException -> ResultApi.HttpError("Проверте подключение к интернету")
        is ApolloSuccessException -> ResultApi.HttpError(e.message.orEmpty())
        is SSLHandshakeException -> ResultApi.HttpError("Ошибка сертификата")
        is ApolloParseException -> ResultApi.HttpError("Невозможно распарсить данные")
        is IOException -> ResultApi.HttpError(e.message, HttpURLConnection.HTTP_INTERNAL_ERROR)
        else -> ResultApi.HttpError("Неизветная ошибка")
    }
}


