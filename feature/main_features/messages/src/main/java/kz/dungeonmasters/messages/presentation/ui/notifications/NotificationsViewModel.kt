package kz.dungeonmasters.messages.presentation.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.dateForNotification
import kz.dungeonmasters.messages.data.entity.Notifications
import kz.dungeonmasters.messages.domain.usecase.GetNotificationsDetailUseCase
import kz.dungeonmasters.messages.domain.usecase.GetNotificationsUseCase
import kz.dungeonmasters.messages.presentation.ui.models.NotificationCardUi

class NotificationsViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val getNotificationsDetailUseCase: GetNotificationsDetailUseCase
) : CoreLaunchViewModel() {

    private val _items = MutableLiveData<List<NotificationCardUi>>()
    val items: LiveData<List<NotificationCardUi>>
        get() = _items

    val notificationUi = MutableLiveData<Notifications>()
    fun getNotifications() {
        launch({
            getNotificationsUseCase.execute(Unit)
        }, {
            it?.let {
                _items.postValue(it.results.map {
                    NotificationCardUi(
                        it.id,
                        it.is_read,
                        "Тесты",
                        dateForNotification(it.created_at),
                        it.title,
                        it.body
                    )
                }
                )
            }
        })
    }

    fun getNotificationDetail(id: String) {
        launch({ getNotificationsDetailUseCase.execute(id) }, {
            it?.let {
                notificationUi.postValue(it)
            }
        })
    }

}