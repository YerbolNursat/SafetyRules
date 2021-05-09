package kz.dungeonmasters.auth.presentation.ui.welcome

import android.os.Bundle
import android.view.View
import kz.application.auth.BR
import kz.application.auth.R
import kz.application.auth.databinding.FragmentAuthBinding
import kz.dungeonmasters.auth.presentation.ui.bottom_items.LogInUi
import kz.dungeonmasters.auth.presentation.ui.bottom_items.RegistrationsPartOneUi
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.ModalBottomSheetDialog
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
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
            listOf(RegistrationsPartOneUi()),
            requireContext(),
            true,
            peekHide = resources.displayMetrics.heightPixels
        )
    }

    private fun showRegistrationPartTwoBottomSheet() {

    }

    private fun showLogInPartOneBottomSheet() {
        ModalBottomSheetDialog(
            listOf(LogInUi()),
            requireContext(),
            true,
            peekHide = resources.displayMetrics.heightPixels
        )
    }

    private fun showResetPasswordPartOneBottomSheet() {

    }

    override fun onStart() {
        super.onStart()

    }

}