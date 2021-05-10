package kz.dungeonmasters.auth.presentation.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.dungeonmasters.auth.domain.usecase.LoginUseCase
import kz.dungeonmasters.auth.domain.usecase.RegisterSendEmailUseCase
import kz.dungeonmasters.auth.domain.usecase.RegisterUseCase
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.events.Event

class AuthViewModel(
    private val registerSendEmailUseCase: RegisterSendEmailUseCase,
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase
) : CoreLaunchViewModel() {
    private var email: String? = null

    private val _sendEmail = MutableLiveData<Event<Any>>()
    val sendEmailUseCase: LiveData<Event<Any>>
        get() = _sendEmail

    private val _register = MutableLiveData<Event<Any>>()
    val register: LiveData<Event<Any>>
        get() = _register

    private val _login = MutableLiveData<Event<Any>>()
    val login: LiveData<Event<Any>>
        get() = _login

    fun registerSendEmail(params: RegisterSendEmailUseCase.Params) {
        email = params.email
        launch({ registerSendEmailUseCase.execute(params) }, {
            _sendEmail.postValue(Event(Any()))
        })
    }

    fun register(params: RegisterUseCase.Params) {
        params.email = email
        launch({ registerUseCase.execute(params) }, {
            _register.postValue(Event(Any()))
        })
    }

    fun login(params: LoginUseCase.Params) {
        launch({ loginUseCase.execute(params) }, {
            _login.postValue(Event(Any()))
        })
    }


}