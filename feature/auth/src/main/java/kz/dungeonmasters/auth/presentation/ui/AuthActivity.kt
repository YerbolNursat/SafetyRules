package kz.dungeonmasters.auth.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kz.application.auth.R
import kz.dungeonmasters.core.core_application.presentation.ui.activities.CoreUnauthorizedActivity

class AuthActivity : CoreUnauthorizedActivity(R.layout.activty_auth) {
    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        window.statusBarColor = ContextCompat.getColor(baseContext, R.color.gradientEnd)
        return super.onCreateView(parent, name, context, attrs)
    }
}