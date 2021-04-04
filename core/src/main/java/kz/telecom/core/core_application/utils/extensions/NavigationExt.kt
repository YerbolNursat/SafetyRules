package kz.telecom.core.core_application.utils.extensions

import android.app.Activity
import android.content.Intent
import android.util.SparseArray
import androidx.core.util.forEach
import androidx.core.util.set
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.telecom.core.core_application.R
import kz.telecom.core.core_application.utils.navigation.AuthActivityNavigator
import kz.telecom.core.core_application.utils.navigation.AuthFragmentNavigator
import kz.telecom.core.core_application.utils.navigation.AuthNavHostFragment.Companion.isUserSuccessAuthorization

/**
 * При успешной авторизации уведомляем навигаю что пользователь авторизован
 */
fun Fragment.successAuth() {
    isUserSuccessAuthorization = true
    activity?.finish()
}

/**
 * Функция работает в методе onResume fragment-a в паре с [AuthFragmentNavigator]
 * [AuthFragmentNavigator] перехватывает переход на фрагмент если данный фрагмент являеться авторизованным
 * то просте успешной авторизации отправляем пользователя на фрагмент перехваченый [AuthFragmentNavigator]
 */
fun Fragment.goPendingFragment() {
    /**
     * Проверяем успешно ли прошла авторизация за  это отвечает [successAuth]
     */
    if (isUserSuccessAuthorization) {
        /**
         * В случае если нужно перейти в другой навигационный граф тогда передает id контейнера фрагмента
         * Если передача viewId являеться пустой то по умолчанию задаеться значение -1
         * в этом случае операция производиться в текушем графе
         */
        val viewId = AuthFragmentNavigator.vieId ?: -1
        val navController = if (viewId == -1) {
            findNavController()
        } else {
            activity?.findNavController(viewId) ?: return
        }

        /**
         * Находим текущий навигат для отчиски данных
         */
        val navigator =
            navController.navigatorProvider.getNavigator(AuthFragmentNavigator::class.java)
        val destinationId = AuthFragmentNavigator.destinationPending?.id ?: -1

        if(destinationId == -1){
            return
        }

        navController.navigate(
            destinationId,
            AuthFragmentNavigator.argsPending,
            AuthFragmentNavigator.navOptionsPending,
            AuthFragmentNavigator.navigatorExtrasPending
        )
        /**
         * Очишаем данные
         */
        navigator.clearPendingData()
    }
}


/**
 * Функция работает в методе onResume fragment-a в паре с [AuthActivityNavigator]
 * [AuthActivityNavigator] перехватывает переход на активити если данный фрагмент являеться авторизованным
 * то просте успешной авторизации отправляем пользователя на activity перехваченное [AuthActivityNavigator]
 */
fun Activity.goPendingActivity() {
    if (isUserSuccessAuthorization) {
        val viewId = AuthActivityNavigator.vieId ?: -1
        if (viewId == -1) {
            return
        }
        /**
         * Находим текущий навигат для отчиски данных
         */
        val navigator =
            findNavController(viewId).navigatorProvider.getNavigator(AuthActivityNavigator::class.java)
        val destinationId = AuthActivityNavigator.destinationPending?.id ?: -1

        if(destinationId == -1){
            return
        }

        findNavController(viewId).navigate(
            destinationId,
            AuthActivityNavigator.argsPending,
            AuthActivityNavigator.navOptionsPending,
            AuthActivityNavigator.navigatorExtrasPending
        )
        /**
         * Очишаем данные
         */
        navigator.clearPendingData()
    }
}




/**
 * Manages the various graphs needed for a [BottomNavigationView].
 *
 * This sample is a workaround until the Navigation Component supports multiple back stacks.
 */
fun BottomNavigationView.setupWithNavController(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
): LiveData<NavController> {

    // Map of tags
    val graphIdToTagMap = SparseArray<String>()
// Result. Mutable live data with the selected controlled
    val selectedNavController = MutableLiveData<NavController>()

    var firstFragmentGraphId = 0

    // First create a NavHostFragment for each NavGraph ID
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            navGraphId,
            containerId
        )

        // Obtain its id
        val graphId = navHostFragment.navController.graph.id

        if (index == 0) {
            firstFragmentGraphId = graphId
        }

        // Save to the map
        graphIdToTagMap[graphId] = fragmentTag

        // Show or hide nav host fragment depending on whether it's the selected item.
        if (this.selectedItemId == graphId) {
            // Update livedata with the selected graph
            selectedNavController.value = navHostFragment.navController
            showNavHostFragment(fragmentManager, navHostFragment, index == 0)
        } else {
            hideNavHostFragment(fragmentManager, navHostFragment)
        }
    }

    // Now connect selecting an item with swapping Fragments
    var selectedItemTag = graphIdToTagMap[this.selectedItemId]
    val firstFragmentTag = graphIdToTagMap[firstFragmentGraphId]
    var isOnFirstFragment = selectedItemTag == firstFragmentTag

    // When a navigation item is selected
    setOnNavigationItemSelectedListener { item ->
        // Don't do anything if the state is state has already been saved.
        if (fragmentManager.isStateSaved) {
            false
        } else {
            val newlySelectedItemTag = graphIdToTagMap[item.itemId]
            if (selectedItemTag != newlySelectedItemTag) {
                // Pop everything above the first fragment (the "fixed start destination")
                fragmentManager.popBackStack(
                    firstFragmentTag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                        as NavHostFragment

                // Exclude the first fragment tag because it's always in the back stack.
                if (firstFragmentTag != newlySelectedItemTag) {
                    // Commit a transaction that cleans the back stack and adds the first fragment
                    // to it, creating the fixed started destination.
                    fragmentManager.beginTransaction()
                        .setMaxLifecycle(selectedFragment, Lifecycle.State.RESUMED)
                        .setCustomAnimations(
                            R.anim.nav_default_enter_anim,
                            R.anim.nav_default_exit_anim,
                            R.anim.nav_default_pop_enter_anim,
                            R.anim.nav_default_pop_exit_anim
                        )
                        .show(selectedFragment)
                        .setPrimaryNavigationFragment(selectedFragment)
                        .apply {
                            // hide all other Fragments
                            graphIdToTagMap.forEach { _, fragmentTagItem ->
                                if (fragmentTagItem != newlySelectedItemTag) {
                                    setMaxLifecycle(fragmentManager.findFragmentByTag(firstFragmentTag)!!, Lifecycle.State.STARTED)
                                    hide(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                                }
                            }
                        }
                        .addToBackStack(firstFragmentTag)
                        .setReorderingAllowed(true)
                        .commit()
                }
                selectedItemTag = newlySelectedItemTag
                isOnFirstFragment = selectedItemTag == firstFragmentTag
                selectedNavController.value = selectedFragment.navController
                true
            } else {
                false
            }
        }
    }

    // Optional: on item reselected, pop back stack to the destination of the graph
    setupItemReselected(graphIdToTagMap, fragmentManager)

    // Handle deep link
    setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

    // Finally, ensure that we update our BottomNavigationView when the back stack changes
    fragmentManager.addOnBackStackChangedListener {
        if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
            this.selectedItemId = firstFragmentGraphId
        }

        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).
        selectedNavController.value?.let { controller ->
            if (controller.currentDestination == null) {
                controller.navigate(controller.graph.id)
            }
        }
    }
    return selectedNavController
}

private fun BottomNavigationView.setupDeepLinks(
    navGraphIds: List<Int>,
    fragmentManager: FragmentManager,
    containerId: Int,
    intent: Intent
) {
    navGraphIds.forEachIndexed { index, navGraphId ->
        val fragmentTag = getFragmentTag(index)

        // Find or create the Navigation host fragment
        val navHostFragment = obtainNavHostFragment(fragmentManager, fragmentTag, navGraphId, containerId)
        // Handle Intent
        if (navHostFragment.navController.handleDeepLink(intent)
            && selectedItemId != navHostFragment.navController.graph.id
        ) {
            this.selectedItemId = navHostFragment.navController.graph.id
        }
    }
}

private fun BottomNavigationView.setupItemReselected(
    graphIdToTagMap: SparseArray<String>,
    fragmentManager: FragmentManager
) {
    setOnNavigationItemReselectedListener { item ->
        val newlySelectedItemTag = graphIdToTagMap[item.itemId]
        val selectedFragment = fragmentManager.findFragmentByTag(newlySelectedItemTag)
                as NavHostFragment
        val navController = selectedFragment.navController
        // Pop the back stack to the start destination of the current navController graph
        navController.popBackStack(
            navController.graph.startDestination, false
        )
    }
}

private fun hideNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment
) {
    fragmentManager.beginTransaction()
        .setMaxLifecycle(navHostFragment, Lifecycle.State.STARTED)
        .hide(navHostFragment)
        .commitNow()
}

private fun showNavHostFragment(
    fragmentManager: FragmentManager,
    navHostFragment: NavHostFragment,
    isPrimaryNavFragment: Boolean
) {
    fragmentManager.beginTransaction()
        .setMaxLifecycle(navHostFragment, Lifecycle.State.RESUMED)
        .show(navHostFragment)
        .apply {
            if (isPrimaryNavFragment) {
                setPrimaryNavigationFragment(navHostFragment)
            }
        }
        .commitNow()

}

private fun obtainNavHostFragment(
    fragmentManager: FragmentManager,
    fragmentTag: String,
    navGraphId: Int,
    containerId: Int
): NavHostFragment {
    // If the Nav Host fragment exists, return it
    val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
    existingFragment?.let { return it }

    // Otherwise, create it and return it.
    val navHostFragment = NavHostFragment.create(navGraphId)
    fragmentManager.beginTransaction()
        .add(containerId, navHostFragment, fragmentTag)
        .commitNow()
    return navHostFragment
}

private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

private fun getFragmentTag(index: Int) = "bottomNavigation#$index"