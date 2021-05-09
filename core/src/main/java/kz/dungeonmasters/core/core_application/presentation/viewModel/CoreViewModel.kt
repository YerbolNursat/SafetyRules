package kz.dungeonmasters.core.core_application.presentation.viewModel

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.dungeonmasters.core.kd_dispatcher.IKDispatcher
import kz.dungeonmasters.core.core_application.data.network.Status
import kz.dungeonmasters.core.core_application.presentation.model.UIValidation
import kz.dungeonmasters.core.core_application.utils.event.SingleLiveEvent
import kz.dungeonmasters.core.core_application.utils.wrappers.EventWrapper
import org.koin.core.KoinComponent

@Deprecated("Использовать [CoreLaunchViewModel]")
abstract class CoreViewModel : ViewModel(), KoinComponent, IKDispatcher {

    /**
     * Вывод ошибки при обрабоке http запросов
     */
    internal val _errorLiveData = MutableLiveData<EventWrapper<String>>()
    val errorLiveData: LiveData<EventWrapper<String>>
        get() = _errorLiveData


    /**
     * Статус при http запросе
     */
    val statusLiveData = MutableLiveData<EventWrapper<Status>>()

    /**
     * Вывод ошибок для конкретного поля
     */
    internal val _errorByTypeLiveData = MutableLiveData<EventWrapper<UIValidation>>()
    val errorByTypeLiveData: LiveData<EventWrapper<UIValidation>>
        get() = _errorByTypeLiveData

    internal val _redirectFragment = SingleLiveEvent<Pair<@IdRes Int, Bundle?>>()
    val redirectFragment: LiveData<Pair<Int, Bundle?>>
        get() = _redirectFragment
}