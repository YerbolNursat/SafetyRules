package kz.dungeonmasters.profile.presentation.ui.models_bottom

import android.view.View
import androidx.databinding.ViewDataBinding
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.BaseBottomSheetDialogItem
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton
import kz.dungeonmasters.core.core_application.utils.extensions.visible
import kz.dungeonmasters.profile.R
import kz.dungeonmasters.profile.databinding.ItemBottomProfileImagesBinding

data class ProfileImagesUi(
    val listOfImages: List<ProfileImageUi>,
    val actionOnClick: ((String,String) -> Unit)
) : BaseBottomSheetDialogItem() {
    lateinit var binding: ItemBottomProfileImagesBinding
    override fun bind(viewBinding: ViewDataBinding, position: Int) {
        when (viewBinding) {
            is ItemBottomProfileImagesBinding -> {
                viewBinding.item = this
                binding = viewBinding
                standardInitButton(
                    CoreButton(
                        "Сохранить",
                        {
                            actionOnClick.invoke(listOfImages.first { it.value.picked }.link,listOfImages.first { it.value.picked }.id)
                            actionForClose?.invoke()
                        }),
                    viewBinding.btnSave.root
                )
            }
        }
    }

    override fun getLayout(): Int = R.layout.item_bottom_profile_images
    override fun initializeViewBinding(view: View): ViewDataBinding =
        ItemBottomProfileImagesBinding.bind(view)

    fun setVisibleButton() {
        binding.btnSave.root.visible()
    }

}