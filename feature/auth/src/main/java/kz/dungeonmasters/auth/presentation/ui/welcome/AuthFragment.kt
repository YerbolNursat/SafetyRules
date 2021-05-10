package kz.dungeonmasters.auth.presentation.ui.welcome

import android.os.Bundle
import android.view.View
import kz.application.auth.BR
import kz.application.auth.R
import kz.application.auth.databinding.FragmentAuthBinding
import kz.dungeonmasters.auth.presentation.ui.bottom_items.LogInUi
import kz.dungeonmasters.auth.presentation.ui.bottom_items.RegistrationPartOneUi
import kz.dungeonmasters.auth.presentation.ui.bottom_items.RegistrationPartTwoUi
import kz.dungeonmasters.core.core_application.data.constants.CoreConstant
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.ModalBottomSheetDialog
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.extensions.showActivityAndClearBackStack
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : CoreFragment<FragmentAuthBinding, AuthViewModel>() {
    override val viewModel: AuthViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }

    private fun initButtons() {
        with(binding) {
            standardInitButton(CoreButton("Регистрация", {
                showRegistrationPartOneBottomSheet()
            }), btnLogIn.root)
            standardInitButton(CoreButton("Вход", {
                showLogInPartOneBottomSheet()
            }), btnRegistration.root)
        }
    }

    private fun showRegistrationPartOneBottomSheet() {
        ModalBottomSheetDialog(
            listOf(RegistrationPartOneUi(::showRegistrationPartTwoBottomSheet)),
            requireContext(),
            true,
            peekHide = resources.displayMetrics.heightPixels
        )
    }

    private fun showRegistrationPartTwoBottomSheet() {
        ModalBottomSheetDialog(
            listOf(RegistrationPartTwoUi(::navigateToMainPage)),
            requireContext(),
            true,
            peekHide = resources.displayMetrics.heightPixels
        )
    }

    private fun showLogInPartOneBottomSheet() {
        ModalBottomSheetDialog(
            listOf(LogInUi(::navigateToMainPage)),
            requireContext(),
            true,
            peekHide = resources.displayMetrics.heightPixels
        )
    }

    private fun showResetPasswordPartOneBottomSheet() {

    }

    private fun navigateToMainPage(){
        activity?.showActivityAndClearBackStack(requireContext(),CoreConstant.MAIN_ACTIVITY)
    }

    override fun onStart() {
        super.onStart()

    }

}