package kz.telecom.core.core_application.utils.navigation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import kz.telecom.core.core_application.R
import kz.telecom.core.core_application.data.prefs.SecurityDataSource
import kz.telecom.core.core_application.utils.extensions.showModuleActivity
import org.koin.core.KoinComponent
import org.koin.core.inject


@Navigator.Name("coreFragment")
class AuthFragmentNavigator(
    private val activity: FragmentActivity,
    manager: FragmentManager,
    containerId: Int,
    private val pathAuthActivity : String
) : FragmentNavigator(activity.baseContext, manager, containerId), KoinComponent {

    /**
     * Сохраняем данные для передачи значений в [goPendingFragment]
     */
    companion object {
        var destinationPending: Destination? = null
        var argsPending: Bundle? = null
        var navOptionsPending: NavOptions? = null
        var navigatorExtrasPending: Navigator.Extras? = null
        var vieId: Int? = -1
    }

    private val authToken by inject<SecurityDataSource>()

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val authDestination = destination as? AuthFragmentDestination

        /**
         * Значение атрибута нуждаеться ли фрагмент в авторизации
         */
        val isAuthFragment = authDestination?.isAuthFragment() ?: false

        /**
         * В случае если операция перехода требуеться из другого графа значение не может быть -1
         */
        val viewId = authDestination?.getViewId() ?: -1

        /**
         * Если AccessToken пустой, тогда останавливаем переход на другой фрагмент, и записываем данные
         * в статичные переменные, для того чтобы перейти после авторизации
         */
        if (authToken.getAccessToken().isNullOrEmpty() && isAuthFragment) {
            activity.showModuleActivity(pathAuthActivity)
            addPendingData(destination, args, navOptions, navigatorExtras, viewId)
            return null
        }

        return super.navigate(destination, args, navOptions, navigatorExtras)
    }


    override fun createDestination(): Destination = AuthFragmentDestination(this)


    private fun addPendingData(
        destination: Destination?,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?,
        viewId: Int?
    ) {
        destinationPending = destination
        argsPending = args
        navOptionsPending = navOptions
        navigatorExtrasPending = navigatorExtras
        vieId = viewId
    }

    fun clearPendingData() {
        destinationPending = null
        argsPending = null
        navOptionsPending = null
        navigatorExtrasPending = null
        AuthNavHostFragment.isUserSuccessAuthorization = false
        vieId = null
    }

    @NavDestination.ClassType(Fragment::class)
    open class AuthFragmentDestination(fragmentNavigator: Navigator<out Destination>) :
        FragmentNavigator.Destination(fragmentNavigator) {

        private var viewId = -1
        private var isAuthFragment = false

        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)

            val a = context.resources.obtainAttributes(
                attrs,
                R.styleable.AuthFragmentNavigator
            )
            val className = a.getString(R.styleable.AuthFragmentNavigator_android_name)
            isAuthFragment = a.getBoolean(R.styleable.AuthFragmentNavigator_authFragment, false)
            viewId = a.getResourceId(
                R.styleable.AuthFragmentNavigator_viewId,
                -1
            )
            className?.let { setClassName(it) }
            a.recycle()
        }

        /**
         * Являеться ли фрагмент авторизаванным
         */
        fun isAuthFragment() = isAuthFragment

        /**
         * id контейнера в котором находиться граф
         */
        fun getViewId() = viewId
    }
}