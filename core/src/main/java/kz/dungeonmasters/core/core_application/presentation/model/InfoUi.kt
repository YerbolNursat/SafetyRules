package kz.dungeonmasters.core.core_application.presentation.model

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.core.core_application.R
import kz.dungeonmasters.core.core_application.databinding.ItemInfoBinding

data class InfoUi(
    val subtitle: String,
    val text: String
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemInfoBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_info
    override fun initializeViewBinding(view: View): ViewDataBinding = ItemInfoBinding.bind(view)
}