package kz.dungeonmasters.core.core_application.utils.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.viewModelScope
import kz.dungeonmasters.core.core_application.data.constants.CoreConstant
import kz.dungeonmasters.core.core_application.data.network.ResultApi
import kz.dungeonmasters.core.core_application.data.network.Status
import kz.dungeonmasters.core.core_application.presentation.model.UIValidation
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreViewModel
import kz.dungeonmasters.core.core_application.utils.wrappers.EventWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.net.ssl.HttpsURLConnection


/**
 * запуск coroutine
 * Дает возможноть принимать свой тип в ответе ошибки
 * @param block suspend функция
 * @param result результат
 * @param errorBlock блок ошибки (по умолчанию String)
 * Если нужно использовать свой тив в ошибке применяем  [launchWithError]
 */
fun <T : Any> CoreViewModel.launch(
    block: suspend () -> ResultApi<T>,
    result: (T?) -> Unit,
    errorBlock: ((String?) -> Unit?)? = null
) {
    viewModelScope.launch(Dispatchers.Main) {
        statusLiveData.value = EventWrapper(Status.SHOW_LOADING)
        val value = block()
        unwrap(value, {
            result(it)
        }, errorBlock)
    }
}


/**
 * запуск coroutine c применением пользовательской ошибки с бэка
 * Дает возможноть принимать свой тип в ответе ошибки
 * @param block suspend функция
 * @param result результат
 * @param errorBlock блок ошибки с типом String
 */
fun <T : Any> CoreViewModel.unwrap(
    result: ResultApi<T>,
    successBlock: (T?) -> Unit,
    errorBlock: ((String) -> Unit?)? = null
) {
    when (result) {
        is ResultApi.Success -> {
            statusLiveData.value = EventWrapper(Status.HIDE_LOADING)
            successBlock(result.data)
        }
        is ResultApi.HttpError<*> -> {
            /**
             * Если лямбда для обработки ошибки не определена
             * Тогда выводим ошибку в liveData
             *
             * Бывают случаи когда ошибку нужно обработать
             */
            val error = result.error as? String ?: CoreConstant.EMPTY
            if (errorBlock == null) {
                _errorLiveData.value = EventWrapper(error)
            } else {
                errorBlock.invoke(error)
            }

            /**
             * Если приходит код 401 и ты имеем токен
             * отправляем в стутус редирект в экран логина или запрос нового токена
             */
            if (result.code == HttpsURLConnection.HTTP_UNAUTHORIZED/* && !getPref().getAccessToken()
                    .isNullOrEmpty()*/
            ) {
                statusLiveData.value = EventWrapper(Status.REDIRECT_LOGIN)
                return
            }

            /**
             * В случае ошибки сервера получаем статус ошибки
             */
            statusLiveData.value = EventWrapper(Status.HIDE_LOADING)
            statusLiveData.value = EventWrapper(Status.ERROR)
        }
    }
}


/**
 * запуск coroutine c применением пользовательской ошибки с бэка
 * Дает возможноть принимать свой тип в ответе ошибки
 * @param block suspend функция
 * @param result результат
 * @param errorBlock блок ошибки (передаем свой тип)
 */
fun <T : Any, V : Any> CoreViewModel.launchWithError(
    block: suspend () -> ResultApi<T>,
    result: (T?) -> Unit,
    errorBlock: ((V?) -> Unit?)? = null
) {
    viewModelScope.launch(Dispatchers.Main) {
        statusLiveData.value = EventWrapper(Status.SHOW_LOADING)
        val value = block()
        unwrapWithError(value, {
            result(it)
        }, errorBlock)
    }
}


/**
 * Обработчик ошибок для viewModel используеться для того чтобы обработать ошибку с пользовательской model
 * @param result результат
 * @param successBlock получание результата (возврашает тип)
 * @param errorBlock получание ошибки (Возврашает тип переданный в [launchWithError])
 */
fun <T : Any, V : Any> CoreViewModel.unwrapWithError(
    result: ResultApi<T>,
    successBlock: (T?) -> Unit,
    errorBlock: ((V) -> Unit?)? = null
) {
    when (result) {
        is ResultApi.Success -> {
            statusLiveData.value = EventWrapper(Status.HIDE_LOADING)
            successBlock(result.data)
        }
        is ResultApi.HttpError<*> -> {
            val error = (result.error as? V) ?: return
            errorBlock?.invoke(error)

            /**
             * Если приходит код 401 и ты имеем токен
             * отправляем в стутус редирект в экран логина или запрос нового токена
             */
            if (result.code == HttpsURLConnection.HTTP_UNAUTHORIZED /*&& !getPref().getAccessToken()
                    .isNullOrEmpty()*/
            ) {
                statusLiveData.value = EventWrapper(Status.REDIRECT_LOGIN)
                return
            }

            /**
             * В случае ошибки сервера получаем статус ошибки
             */
            statusLiveData.value = EventWrapper(Status.HIDE_LOADING)
            statusLiveData.value = EventWrapper(Status.ERROR)
        }
    }
}



/**
 * Выводим сообщение об ошибке
 */
fun CoreViewModel.showError(msg: String) {
    _errorLiveData.value = EventWrapper(msg)
}

/**
 * Перенаправление на нужный фрагмент
 * @param [action] отправляем id action-а который приписываеться в nav графе
 * @param [msg] сообщение может быть любого характера
 */

fun CoreViewModel.redirectToFragment(@IdRes action: Int, bundle: Bundle? = null) {
    _redirectFragment.value = Pair(action, bundle)
}

/**
 * Выводим сообщение для определенного поля
 * @param errorMessage сообщение которые показываем на UI
 * @param type тип например Type.password (задаем в текущем модуле для определенного поля)
 */
fun CoreViewModel.showErrorByType(errorMessage: String?, type: String?) {
    _errorByTypeLiveData.value = EventWrapper(UIValidation(type.orEmpty(), errorMessage.orEmpty()))
}
