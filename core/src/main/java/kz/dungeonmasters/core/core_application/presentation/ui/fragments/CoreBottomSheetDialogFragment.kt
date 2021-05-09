package kz.dungeonmasters.core.core_application.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sun.easysnackbar.BaseTransientBar
import com.sun.easysnackbar.EasySnackBar
import kz.dungeonmasters.core.core_application.R
import kz.dungeonmasters.core.core_application.data.constants.CoreConstant
import kz.dungeonmasters.core.core_application.data.network.Status
import kz.dungeonmasters.core.core_application.presentation.model.UIValidation
import kz.dungeonmasters.core.core_application.presentation.viewModel.CoreLaunchViewModel
import kz.dungeonmasters.core.core_application.utils.callback.PermissionHandler
import kz.dungeonmasters.core.core_application.utils.callback.ResultLiveDataHandler
import kz.dungeonmasters.core.core_application.utils.wrappers.EventObserver
import kz.dungeonmasters.core.kd_dispatcher.IKDispatcher

abstract class CoreBottomSheetDialogFragment<VB : ViewDataBinding, VM : CoreLaunchViewModel> : BottomSheetDialogFragment(),
    ResultLiveDataHandler, PermissionHandler, IKDispatcher {
    /**
     * Для того чтобы отслеживать статусы необходимо подписаться во Fragment-е
     */
    protected val statusObserver = EventObserver<Status> {
        it?.let {
            when (it) {
                Status.SHOW_LOADING -> showLoader()
                Status.HIDE_LOADING -> hideLoader()
                Status.SHOW_PULL_TO_REFRESH_LOADING -> showPullToRefreshLoader()
                Status.HIDE_PULL_TO_REFRESH_LOADING -> hidePullToRefreshLoader()
                Status.SHOW_PAGGING_LOADING -> showPagingLoader()
                Status.HIDE_PAGGING_LOADING -> hidePagingLoader()
                Status.SUCCESS -> success()
                else -> return@let
            }
        }
    }


    protected val loaderByTypeObserver = EventObserver<Pair<String, Boolean>> {
        val (type, isLoading) = it
        if (isLoading) {
            showLoaderByType(type)
        } else {
            hideLoaderByType(type)
        }
    }

    protected val errorMessageObserver = EventObserver<String> {
        error(it)
    }

    protected val successMessageObserver = EventObserver<String> {
        successMessage(it)
    }

    /**
     * Подписка на ошибки
     * Возврашает строку и тип ошибки, удобно когда нужно вывести ошибку для конкретного случая
     */
    protected val errorMessageByTypeObserver = EventObserver<UIValidation> {
        errorByType(type = it.type, msg = it.message)
    }

    protected lateinit var binding: VB

    abstract val viewModel: VM

    abstract fun getBindingVariable(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bindingVariable = getBindingVariable()
        binding.setVariable(bindingVariable, viewModel)

        binding.executePendingBindings()
    }

    abstract fun getLayoutRes(): Int

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

    override fun onStart() {
        super.onStart()
        viewModel.statusLiveData.observe(viewLifecycleOwner, statusObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorMessageObserver)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun showLoader() {
        super.showLoader()
        viewModel.loading.postValue(true)
    }

    override fun hideLoader() {
        super.hideLoader()
        viewModel.loading.postValue(false)
    }

    override fun error(msg: String) {
        super.error(msg)
        val contentView = EasySnackBar.convertToContentView(
            requireView(),
            R.layout.content_snackbar_error
        )
        contentView.findViewById<TextView>(R.id.tv_error).text = msg
        EasySnackBar.make(requireView(), contentView, BaseTransientBar.LENGTH_LONG, false).show()
    }

    override fun successMessage(msg: String) {
        super.error(msg)
        val contentView = EasySnackBar.convertToContentView(
            requireView(),
            R.layout.content_snackbar_success
        )
        contentView.findViewById<TextView>(R.id.tv_success).text = msg
        EasySnackBar.make(requireView(), contentView, BaseTransientBar.LENGTH_LONG, false).show()
    }


}