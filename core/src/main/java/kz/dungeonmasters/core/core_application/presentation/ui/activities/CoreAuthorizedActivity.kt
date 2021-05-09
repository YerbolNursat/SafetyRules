package kz.dungeonmasters.core.core_application.presentation.ui.activities

import android.os.Bundle
import android.view.MotionEvent
import kz.dungeonmasters.core.core_application.data.constants.CoreVariables
import kz.dungeonmasters.core.core_application.presentation.controllers.TrackUseApplication
import kz.dungeonmasters.core.core_application.presentation.controllers.TrackUseApplicationController
import kz.dungeonmasters.core.core_application.utils.extensions.goPendingActivity
import kz.dungeonmasters.core.core_application.utils.extensions.showActivityAndClearBackStack

/**
 * Использовать в авторизованой зоне
 * @param lay layout
 * @param isUseLocalSession если нужно использовать локальную сессию
 */
abstract class CoreAuthorizedActivity(
    lay: Int,
    isUseLocalSession: Boolean = true,
    private val isGoToPendingFragment: Boolean = true
) :
    CoreActivity(lay),
    TrackUseApplication by TrackUseApplicationController(isUseLocalSession) {

    override fun redirectLogin() = showActivityAndClearBackStack(CoreVariables.LOGIN_ACTIVITY)

    override fun onCreate(savedInstanceState: Bundle?) {
        onStartTrack(this)
        super.onCreate(savedInstanceState)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        onTouchEvent()
        return super.dispatchTouchEvent(ev)
    }


    override fun onResume() {
        super.onResume()
        onResumeTrack()
        if (isGoToPendingFragment)
            goPendingActivity()
    }

    override fun onPause() {
        super.onPause()
        onPauseTrack()
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroyTrack()
    }

}