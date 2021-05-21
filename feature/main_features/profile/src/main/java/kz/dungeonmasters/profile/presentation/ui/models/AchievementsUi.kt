package kz.dungeonmasters.profile.presentation.ui.models

import android.text.format.DateUtils
import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.profile.R
import kz.dungeonmasters.profile.databinding.ItemAchievementsBinding

data class AchievementsUi(
    val body: String,
    var created_at: String,
    val id: Int,
    val is_done: Boolean,
    val title: String
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemAchievementsBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_achievements
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemAchievementsBinding.bind(view)
}