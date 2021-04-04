package kz.telecom.core.core_application.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.telecom.core.kd_dispatcher.IKDispatcher
import kz.telecom.core.core_application.data.constants.CoreConstant
import kz.telecom.core.core_application.data.network.Status
import kz.telecom.core.core_application.presentation.model.UIValidation
import kz.telecom.core.core_application.utils.callback.PermissionHandler
import kz.telecom.core.core_application.utils.callback.ResultLiveDataHandler
import kz.telecom.core.core_application.utils.delegates.*
import kz.telecom.core.core_application.utils.extensions.toast
import kz.telecom.core.core_application.utils.wrappers.EventObserver


abstract class CoreActivity(lay: Int) : AppCompatActivity(lay), ResultLiveDataHandler,
    DarkTheme by DarkThemeDelegate(),
    TransitionAnimation by TransitionAnimationActivityDelegate(), PermissionHandler, IKDispatcher {

    protected val errorMessageObserver = EventObserver<String> { toast(it) }

    protected val errorMessageByTypeObserver = EventObserver<UIValidation> {
        errorByType(type = it.type, msg = it.message)
    }

    protected val successMessageObserver = EventObserver<String> {
        successMessage(it)
    }


    protected val loaderByTypeObserver = EventObserver<Pair<String, Boolean>> {
        val (type, isLoading) = it
        if (isLoading) {
            showLoaderByType(type)
        } else {
            hideLoaderByType(type)
        }
    }


    open fun redirectLogin() {
        // реализовать в случае базовой функциональности
    }

    /**
     * Для того чтобы отслеживать статусы необходимо подписаться в Activity
     */
    protected val statusObserver = EventObserver<Status> {
        it?.let {
            when (it) {
                Status.SHOW_LOADING -> showLoader()
                Status.HIDE_LOADING -> hideLoader()
                Status.REDIRECT_LOGIN -> redirectLogin()
                Status.SHOW_PULL_TO_REFRESH_LOADING -> showPullToRefreshLoader()
                Status.HIDE_PULL_TO_REFRESH_LOADING -> hidePullToRefreshLoader()
                Status.SHOW_PAGGING_LOADING -> showPagingLoader()
                Status.HIDE_PAGGING_LOADING -> hidePagingLoader()
                Status.SUCCESS -> success()
                else -> return@let
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        initTheme(window, Theme.DARK)
        initTransition(this)
        super.onCreate(savedInstanceState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            when {
                it != CoreConstant.PERMISSION_DENIED -> {
                    confirmPermission()
                    confirmWithRequestCode(requestCode)
                    return
                }
                else -> {
                    ignorePermission()
                    return
                }
            }
        }
    }


}


