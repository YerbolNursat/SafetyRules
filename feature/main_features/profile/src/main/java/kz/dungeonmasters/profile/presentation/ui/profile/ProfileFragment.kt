package kz.dungeonmasters.profile.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kz.dungeonmasters.core.core_application.data.constants.CoreConstant
import kz.dungeonmasters.core.core_application.data.prefs.SecurityDataSource
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.ModalBottomSheetDialog
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.loadImage
import kz.dungeonmasters.core.core_application.utils.extensions.showActivityAndClearBackStack
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton
import kz.dungeonmasters.profile.BR
import kz.dungeonmasters.profile.R
import kz.dungeonmasters.profile.data.entity.ProfileInfo
import kz.dungeonmasters.profile.databinding.FragmentProfileBinding
import kz.dungeonmasters.profile.presentation.ui.models_bottom.InfoUi
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : CoreFragment<FragmentProfileBinding, ProfileViewModel>() {

    private val securityDataSource: SecurityDataSource by inject()

    override val viewModel: ProfileViewModel by viewModel()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        viewModel.getProfileInfo()
    }

    private fun initButtons() {
        standardInitButton(CoreButton("Выйти", (::logOut)), binding.btnLogOut.root)
        binding.btnToEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        binding.btnToAppRules.setOnClickListener {
            ModalBottomSheetDialog(
                listOf(
                    InfoUi(
                        "Политика конфиденциальности",
                        "Заголовок",
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                                "\n" +
                                "Lorem Ipsum\n" +
                                "Lorem Ipsum\n" +
                                "Lorem Ipsum"
                    )
                ),
                requireContext(),
                true,
                peekHide = resources.displayMetrics.heightPixels
            )
        }
        binding.btnToConfidential.setOnClickListener {
            ModalBottomSheetDialog(
                listOf(
                    InfoUi(
                        "Политика конфиденциальности",
                        "Заголовок",
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                                "\n" +
                                "Lorem Ipsum\n" +
                                "Lorem Ipsum\n" +
                                "Lorem Ipsum"
                    )
                ),
                requireContext(),
                true,
                peekHide = resources.displayMetrics.heightPixels
            )
        }
        binding.btnToDeveloper.setOnClickListener {
            ModalBottomSheetDialog(
                listOf(
                    InfoUi(
                        "Политика конфиденциальности",
                        "Заголовок",
                        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\n" +
                                "\n" +
                                "Lorem Ipsum\n" +
                                "Lorem Ipsum\n" +
                                "Lorem Ipsum"
                    )
                ),
                requireContext(),
                true,
                peekHide = resources.displayMetrics.heightPixels
            )
        }
    }

    private fun logOut() {
        securityDataSource.clearAuthorizedUserData()
        activity?.showActivityAndClearBackStack(requireContext(), CoreConstant.AUTH_ACTIVITY)
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(viewLifecycleOwner, Observer(::handleItems))
    }

    private fun handleItems(data: ProfileInfo) {
        data.photo?.let { binding.userIv.loadImage(it) }
        data.username?.let { binding.userNameTv.text = it }
        data.email.let { binding.emailTv.text = it }
    }

}