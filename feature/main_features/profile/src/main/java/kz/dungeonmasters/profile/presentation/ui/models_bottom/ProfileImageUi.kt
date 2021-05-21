package kz.dungeonmasters.profile.presentation.ui.models_bottom

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ViewDataBinding
import com.xwray.groupie.viewbinding.BindableItem
import kz.dungeonmasters.profile.BR
import kz.dungeonmasters.profile.R
import kz.dungeonmasters.profile.databinding.ItemProfileImageBinding

data class ProfileImageUi(
    val id: String,
    val link: String,
    val onClick: ((String) -> Unit)
) : BindableItem<ViewDataBinding>() {
    val value = Value()
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemProfileImageBinding -> {
                viewBinding.item = this
                viewBinding.root.setOnClickListener {
                    onClick.invoke(id)
                }
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_profile_image
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemProfileImageBinding.bind(view)

    class Value : BaseObservable() {

        @Bindable
        var picked: Boolean = false
            set(value) {
                field = value
                notifyPropertyChanged(BR.picked)
            }

    }
}