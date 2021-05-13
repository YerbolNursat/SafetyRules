package kz.dungeonmasters.messages.presentation.ui.notifications

import android.os.Bundle
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.messages.BR
import kz.dungeonmasters.messages.R
import kz.dungeonmasters.messages.databinding.FragmentNotificationBinding
import kz.dungeonmasters.messages.presentation.ui.models.NotificationCardUi
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationsFragment : CoreFragment<FragmentNotificationBinding, NotificationsViewModel>() {

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override val viewModel: NotificationsViewModel by viewModel()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_notification

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setData()
    }

    private fun initViews() {
        binding.notificationRv.adapter = groupAdapter
    }

    private fun setData() {
        val data = listOf(
            NotificationCardUi(false,"Достижение","Сегодня","Теория","Поздравляем! Вы впервые изучили теорию"),
            NotificationCardUi(true,"Достижение","Сегодня","Тест","Поздравляем! Вы впервые прошли тест"),
            NotificationCardUi(true,"Достижение","Сегодня","Тест","Поздравляем! Вы прошли тест без единой ошибки"),
        )
        groupAdapter.updateAsync(data)
    }

}