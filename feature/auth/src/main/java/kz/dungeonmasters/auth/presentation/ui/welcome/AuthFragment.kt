package kz.dungeonmasters.auth.presentation.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kz.application.auth.BR
import kz.application.auth.R
import kz.application.auth.databinding.FragmentAuthBinding
import kz.dungeonmasters.auth.presentation.ui.bottom_items.LogInUi
import kz.dungeonmasters.auth.presentation.ui.bottom_items.RegistrationPartOneUi
import kz.dungeonmasters.auth.presentation.ui.bottom_items.RegistrationPartTwoUi
import kz.dungeonmasters.core.core_application.data.constants.CoreConstant
import kz.dungeonmasters.core.core_application.data.prefs.SecurityDataSource
import kz.dungeonmasters.core.core_application.presentation.content.CoreButton
import kz.dungeonmasters.core.core_application.presentation.ui.dialogs.ModalBottomSheetDialog
import kz.dungeonmasters.core.core_application.presentation.ui.fragments.CoreFragment
import kz.dungeonmasters.core.core_application.utils.events.EventObserver
import kz.dungeonmasters.core.core_application.utils.extensions.showActivityAndClearBackStack
import kz.dungeonmasters.core.core_application.utils.extensions.standardInitButton
import kz.dungeonmasters.core.core_application.utils.extensions.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class AuthFragment : CoreFragment<FragmentAuthBinding, AuthViewModel>() {

    private var registrationPartOneBottomSheet: ModalBottomSheetDialog? = null
    private var registrationPartTwoBottomSheet: ModalBottomSheetDialog? = null
    private var loginBottomSheet: ModalBottomSheetDialog? = null

    private val securityDataSource: SecurityDataSource by inject()

    override val viewModel: AuthViewModel by viewModel()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutRes(): Int = R.layout.fragment_auth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (securityDataSource.getAccessToken().isNullOrEmpty() || securityDataSource.getRefreshToken().isNullOrEmpty()) {
            binding.llButtons.visible()
            initButtons()
        } else {
            viewLifecycleOwner.lifecycleScope.launch {
                delay(2000)
                navigateToMainPage(Any())
            }
        }
    }

    private fun initButtons() {
        with(binding) {
            standardInitButton(CoreButton("Регистрация", {
                showRegistrationPartOneBottomSheet()
            }), btnLogIn.root)
            standardInitButton(CoreButton("Вход", {
                showLogInBottomSheet()
            }), btnRegistration.root)
        }
    }

    override fun showLoader() {
        super.showLoader()
        registrationPartOneBottomSheet?.showLoader()
        registrationPartTwoBottomSheet?.showLoader()
        loginBottomSheet?.showLoader()
    }

    override fun hideLoader() {
        super.hideLoader()
        registrationPartOneBottomSheet?.hideLoader()
        registrationPartTwoBottomSheet?.hideLoader()
        loginBottomSheet?.hideLoader()
    }

    override fun onStart() {
        super.onStart()
        viewModel.sendEmailUseCase.observe(
            viewLifecycleOwner,
            EventObserver(::showRegistrationPartTwoBottomSheet)
        )
        viewModel.register.observe(viewLifecycleOwner, EventObserver(::navigateToMainPage))
        viewModel.login.observe(viewLifecycleOwner, EventObserver(::navigateToMainPage))
    }

    private fun showRegistrationPartOneBottomSheet() {
        with(viewModel) {
            registrationPartOneBottomSheet = ModalBottomSheetDialog(
                listOf(RegistrationPartOneUi(::registerSendEmail)),
                requireContext(),
                true,
                peekHide = resources.displayMetrics.heightPixels
            )
        }
    }

    private fun showRegistrationPartTwoBottomSheet(data: Any) {
        with(viewModel) {
            registrationPartOneBottomSheet?.dismiss()
            registrationPartTwoBottomSheet = ModalBottomSheetDialog(
                listOf(RegistrationPartTwoUi(::register)),
                requireContext(),
                true,
                peekHide = resources.displayMetrics.heightPixels
            )
        }
    }

    private fun showLogInBottomSheet() {
        with(viewModel) {
            loginBottomSheet = ModalBottomSheetDialog(
                listOf(LogInUi(::login)),
                requireContext(),
                true,
                peekHide = resources.displayMetrics.heightPixels
            )
        }
    }

    private fun showResetPasswordPartOneBottomSheet() {

    }

    private fun navigateToMainPage(data: Any?) {
        registrationPartTwoBottomSheet?.dismiss()
        registrationPartOneBottomSheet?.dismiss()
        loginBottomSheet?.dismiss()
        activity?.showActivityAndClearBackStack(requireContext(), CoreConstant.MAIN_ACTIVITY)
    }

}