package com.study.rev.androidrevolution

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import kotlinx.android.synthetic.main.fragment_gallery.*
import android.webkit.WebView



class CafeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = NavigationDrawerConstants.TAG_CAFE

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View? = inflater.inflate(R.layout.fragment_gallery, container, false)

        //왭 설정
        val webView = view!!.findViewById<WebView>(R.id.webView)


        webView.settings.let {
            it.javaScriptEnabled = true
            it.databaseEnabled = true
            it.setAppCacheEnabled(true)
            it.domStorageEnabled = true
            it.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            CookieSyncManager.createInstance(context)
        }

        webView.webViewClient = object: WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                {
                    CookieSyncManager.getInstance().sync()
                }
                else {
                    CookieManager.getInstance().flush()
                }
               // UMLView.text = view!!.url
                super.onPageFinished(view, url)
            }
        }

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)

        webView.loadUrl("https://cafe.naver.com/rev2010")

       // UMLView.movementMethod = ScrollingMovementMethod()
        // UMLView.text = webView.url


        return view
    }

    override fun onResume() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            CookieSyncManager.getInstance().startSync()
        }
        super.onResume()

    }

    override fun onPause() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            CookieSyncManager.getInstance().stopSync()
        }
        super.onPause()

    }

}