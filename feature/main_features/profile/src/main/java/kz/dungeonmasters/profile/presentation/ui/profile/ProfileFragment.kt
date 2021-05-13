package kz.dungeonmasters.profile.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.dungeonmasters.core.core_application.data.constants.CoreConstant
import kz.dungeonmasters.core.core_application.data.prefs.SecurityDataSource
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.showActivityAndClearBackStack
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton
import kz.dungeonmasters.profile.BR
import kz.dungeonmasters.profile.R
import kz.dungeonmasters.profile.databinding.FragmentProfileBinding
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
    }

    private fun initButtons() {
        standardInitButton(CoreButton("Выйти", (::logOut)), binding.btnLogOut.root)
        binding.btnToEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }

    private fun logOut() {
        securityDataSource.clearAuthorizedUserData()
        activity?.showActivityAndClearBackStack(requireContext(), CoreConstant.AUTH_ACTIVITY)
    }


}