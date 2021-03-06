package kz.dungeonmasters.main.presentation.ui

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.dungeonmasters.core.core_application.presentation.ui.Navigator
import kz.dungeonmasters.core.core_application.presentation.ui.activities.CoreAuthorizedActivity
import kz.dungeonmasters.core.core_application.utils.extensions.gone
import kz.dungeonmasters.core.core_application.utils.extensions.setupWithNavController
import kz.dungeonmasters.core.core_application.utils.extensions.visible
import kz.dungeonmasters.main.R
import timber.log.Timber

class MainActivity : CoreAuthorizedActivity(lay = R.layout.activity_main), Navigator {
    private var currentNavController: LiveData<NavController>? = null
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        bottomNavigationView = findViewById(R.id.bottom_nav)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun openDeepLink(url: String?) {
        url?.let {
            try {
                currentNavController?.value?.navigate(Uri.parse(url))
            } catch (e: Exception) {
                e.printStackTrace()
                finish()
            }
        }
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navGraphIds = listOf(
            R.navigation.home_nav,
            R.navigation.search_nav,
            R.navigation.tests_nav,
            R.navigation.messages_nav,
            R.navigation.profile_nav,
        )

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller

        controller.observe(this, Observer(::onControllerChanged))
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }


    private fun onControllerChanged(data: NavController) {
        Timber.i("onControllerChanged, data: $data")
        data.addOnDestinationChangedListener { _, destination, _ ->
            Timber.i("addOnDestinationChangedListener ${destination.id}")
            with(findViewById<BottomNavigationView>(R.id.bottom_nav)) {
                when (destination.id) {
                    R.id.pdfViewerFragment -> gone()
                    else -> visible()
                }
            }
        }
    }

    override fun toTest(TestId: String) {

        bottomNavigationView.selectedItemId = R.id.tests_nav
        openDeepLink(DEEP_LINK_TEST + TestId)

    }

    companion object {
        const val DEEP_LINK_TEST = "https://forward.dungeonmasters.kz/test?TestId="
    }

}