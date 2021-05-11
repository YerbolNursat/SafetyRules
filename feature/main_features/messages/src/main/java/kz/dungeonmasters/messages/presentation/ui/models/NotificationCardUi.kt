package kz.dungeonmasters.messages.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.messages.R
import kz.dungeonmasters.messages.databinding.ItemNotificationCardBinding

data class NotificationCardUi(
    val isRead:Boolean
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemNotificationCardBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_notification_card
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemNotificationCardBinding.bind(view)
}