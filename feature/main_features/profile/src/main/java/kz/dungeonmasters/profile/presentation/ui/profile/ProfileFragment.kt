package kz.dungeonmasters.profile.presentation.ui.profile

import android.os.Bundle
import android.view.View
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton
import kz.dungeonmasters.profile.BR
import kz.dungeonmasters.profile.R
import kz.dungeonmasters.profile.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : CoreFragment<FragmentProfileBinding, ProfileViewModel>() {

    override val viewModel: ProfileViewModel by viewModel()
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }

    private fun initButtons() {
        standardInitButton(CoreButton("Выйти", {}), binding.btnLogOut.root)
    }
}