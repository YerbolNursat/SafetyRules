package kz.dungeonmasters.home.presentation.ui.models

import android.view.View
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.home.R
import kz.dungeonmasters.home.databinding.ItemComicsBinding
import timber.log.Timber

data class ComicsUi(
    val code: String,
    val title: String?,
    val url: String,
    val icon: String,
    var actionOnClick: ((String, String) -> Unit)? = null
) : BindableItem<ViewDataBinding>() {
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemComicsBinding -> {
                viewBinding.item = this
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_comics
    override fun initializeViewBinding(view: View): ViewDataBinding = ItemComicsBinding.bind(view)

    fun onClick() {
        Timber.i("ComicsUi, onClick")
        actionOnClick?.invoke(code, title ?: "")
    }

}