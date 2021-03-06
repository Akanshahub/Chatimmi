package com.chatimmi.usermainfragment.otherfragment.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityPrivacyPolicyBinding

@Suppress("DEPRECATION")
class PrivacyPolicy : BaseActivitykt() {
    private var binding: ActivityPrivacyPolicyBinding? = null
    var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        val bundle = intent.extras
        if (bundle != null) {
            url = bundle.getString("privacyPolicy", "")
            Log.d("tag", "privacyPolicy: $url")
        }
        initialSetup()

    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun initialSetup() {
        binding!!.backButton.setOnClickListener {
            onBackPressed()
        }

        binding!!. webViewLayout.settings.loadsImagesAutomatically = true
        binding!!. webViewLayout.settings.javaScriptEnabled = true
        binding!!. webViewLayout.settings.domStorageEnabled = true
        binding!!. webViewLayout.webViewClient = MyBrowser()
        //webViewLayout.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        binding!!. webViewLayout.visibility = View.VISIBLE
        binding!!. webViewLayout.settings.builtInZoomControls = true
        binding!!. webViewLayout.settings.setSupportZoom(true)
        binding!!. webViewLayout.settings.defaultZoom = WebSettings.ZoomDensity.FAR
        binding!!. webViewLayout.settings.javaScriptCanOpenWindowsAutomatically = true
        binding!!. webViewLayout.settings.allowFileAccess = true
        binding!!. webViewLayout.settings.loadWithOverviewMode = true
        binding!!.  webViewLayout.settings.useWideViewPort = true
        binding!!. webViewLayout.settings.pluginState = WebSettings.PluginState.ON
        binding!!. webViewLayout.settings.allowContentAccess = true
        binding!!.webViewLayout.loadUrl(url)
    }

    private inner class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            hideLoader()
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            showLoader()
        }

        override fun onPageCommitVisible(view: WebView?, url: String?) {
            super.onPageCommitVisible(view, url)
        }
    }
}