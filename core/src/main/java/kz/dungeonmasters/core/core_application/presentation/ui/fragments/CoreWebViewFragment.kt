package kz.dungeonmasters.core.core_application.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.core.view.isVisible
import kz.dungeonmasters.core.core_application.R
import kz.dungeonmasters.core.core_application.utils.callback.OnBackPressedHandler
import kz.dungeonmasters.core.core_application.utils.extensions.init

/**
 * Created by Yergali Zhakhan on 5/12/20.
 */

/*
class CoreWebViewFragment: CoreFragment(), OnBackPressedHandler {

    companion object {
        // Пока что урл статичный, потом сделаем чтобы могли передавать как парам при необходимости
        const val URL = "https://extrorse-semiconduc.000webhostapp.com/"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView.init(URL)
        webView.webViewClient = CustomWebViewClient(progressBar)
    }

    override fun backButtonClickEvent() {
        if(webView.canGoBack()) {
            webView.goBack()
        }
    }

    fun canGoBackInWebView() : Boolean {
        return webView.canGoBack()
    }

    private class CustomWebViewClient(
        private val progressBar: ProgressBar
    ): WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.isVisible = false
        }

    }

}*/
