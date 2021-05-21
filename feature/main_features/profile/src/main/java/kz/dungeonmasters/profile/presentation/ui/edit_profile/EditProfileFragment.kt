package kz.dungeonmasters.profile.presentation.ui.edit_profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.ModalBottomSheetDialog
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.events.EventObserver
import kz.dungeonmasters.core.core_application.utils.extensions.loadImage
import kz.dungeonmasters.profile.BR
import kz.dungeonmasters.profile.R
import kz.dungeonmasters.profile.data.entity.ProfileInfo
import kz.dungeonmasters.profile.databinding.FragmentEditProfileBinding
import kz.dungeonmasters.profile.presentation.ui.models_bottom.ProfileImagesUi
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment : CoreFragment<FragmentEditProfileBinding, EditProfileViewModel>() {
    override val viewModel: EditProfileViewModel by viewModel()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_edit_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfileInfo()
        binding.btnSave.setOnClickListener {
            viewModel.setProfileInfo(
                ProfileInfo(
                    binding.emailEt.text.toString(),
                    setGender(binding.genderEt.text.toString()),
                    binding.phoneNumberEt.text.toString(),
                    viewModel.imageId,
                    binding.usernameEt.text.toString(),
                )
            )
        }
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.imageIv.setOnClickListener {
            viewModel.getProfilePhotos()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
        viewModel.photos.observe(viewLifecycleOwner, EventObserver(::showPhotos))
        viewModel.setImage.observe(viewLifecycleOwner, EventObserver(::setImage))
        viewModel.newDestination.observe(viewLifecycleOwner, EventObserver(::newDestination))
    }

    private fun showPhotos(data: ProfileImagesUi) {
        ModalBottomSheetDialog(
            listOf(data),
            requireContext(),
            transparent = true
        )
    }

    private fun newDestination(event: Unit) {
        findNavController().popBackStack()
    }

    private fun handleItems(data: ProfileInfo) {
        data.photo?.let { binding.imageIv.loadImage(it) }
        data.username?.let { binding.usernameEt.setText(it) }
        data.email.let { binding.emailEt.setText(it) }
        data.phone_number?.let { binding.phoneNumberEt.setText(it) }
        data.gender?.let { binding.genderEt.setText(it) }
    }

    private fun setImage(url: String) {
        binding.imageIv.loadImage(url)
    }

    private fun setGender(gender: String): String {
        return when (gender.toLowerCase()) {
            "мужской" -> "male"
            "женский" -> "female"
            else -> "others"
        }
    }

}