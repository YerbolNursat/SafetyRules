package kz.dungeonmasters.messages.presentation.ui.notifications

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kz.dungeonmasters.core.core_application.presentation.model.InfoResponseUi
import kz.dungeonmasters.core.core_application.presentation.model.InfoUi
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.ModalBottomSheetDialog
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.messages.BR
import kz.dungeonmasters.messages.R
import kz.dungeonmasters.messages.data.entity.Notifications
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
        viewModel.getNotifications()
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.getNotifications()
        }
    }

    private fun initViews() {
        binding.notificationRv.adapter = groupAdapter
    }


    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
        viewModel.notificationUi.observe(viewLifecycleOwner, Observer(::showNotification))
    }

    private fun showNotification(data: Notifications) {
        ModalBottomSheetDialog(
            listOf(
                InfoResponseUi(
                    "Tecты",
                    listOf(
                        InfoUi(data.title ?: "", data.body ?: ""),
                    )
                )
            ),
            requireContext(),
            true,
            peekHide = resources.displayMetrics.heightPixels
        )
    }

    private fun handleItems(data: List<NotificationCardUi>) {
        groupAdapter.updateAsync(data)
        groupAdapter.setOnItemClickListener { item, view ->
            when (item) {
                is NotificationCardUi -> {
                    viewModel.getNotificationDetail(item.id)
                }
            }
        }

    }

}