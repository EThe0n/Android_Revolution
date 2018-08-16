package com.study.rev.androidrevolution


import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.net.NetworkInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.webkit.WebView
import android.net.ConnectivityManager
import android.widget.Toast


class CafeFragment : Fragment() ,onKeyBackPressedListener {  // onKeyBackPressedListener 라는 인터페이스 를 사용하여 뒤로가는 기능을 구현

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = NavigationDrawerConstants.TAG_CAFE

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View? = inflater.inflate(R.layout.fragment_cafe, container, false)

        //왭 설정
        val webView = view!!.findViewById<WebView>(R.id.webView)

        // 네트워크 확인 하여 연결 안되었을시 알려주는
        var connMgr : ConnectivityManager? = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        var networkInfo : NetworkInfo? = connMgr?.activeNetworkInfo
        if(networkInfo != null && networkInfo.isConnected)
        else{
            Toast.makeText(getActivity(), "네트워크에 연결되어있지 않습니다.", Toast.LENGTH_SHORT).show()
        }

        //네트워크 확인 하여 연결 안되었을시 알려주는


        // 왭뷰 설정 디비,캐쉬,자바스크립트 등앱에서 사용가능 하도록
        webView.settings.let {
            it.javaScriptEnabled = true
            it.databaseEnabled = true
            it.setAppCacheEnabled(true)
            it.domStorageEnabled = true
            it.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        // 왭뷰 설정 디비,캐쉬,자바스크립트 등앱에서 사용가능 하도록

        //롤립팝이하 버전을 위한 CookieSyncManageR 는이제 사람짐
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            CookieSyncManager.createInstance(context)
        }
        // 롤립팝이하 버전을 위한

        // 왭뷰 클라이언트를 정의  여기서 함수들을  오버라이드 해서 왭 기능을 조금씩 수정 합니다,
        webView.webViewClient = object: WebViewClient(){

            // 로딩시 불려지는함수
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

                return super.shouldOverrideUrlLoading(view, request)
            }

            //페이지가 끝날때 불려지는함수
            override fun onPageFinished(view: WebView?, url: String?) {
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                {
                    CookieSyncManager.getInstance().sync()  // 쿠키 싱므를 맞춰주는 이전버전방법
                }
                else {
                    CookieManager.getInstance().flush() //  쿠키 싱므를 맞춰주는 현재버전방법
                }
               // UMLView.text = view!!.url
                super.onPageFinished(view, url)
            }
        }


        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)  //
        webView.loadUrl("https://cafe.naver.com/rev2010")

       // UMLView.movementMethod = ScrollingMovementMethod()
        // UMLView.text = webView.url

        return view
    }

    override fun onBack() {  // 뒤로가는 버튼이 눌렸을떄 불리는 함수, MAIN ACTIVITY 에서 불린다
        val webView = view!!.findViewById<WebView>(R.id.webView)
        if(webView.canGoBack()) { // 뒤로 갈수 있게 한다,
            webView.goBack()
        }
        else{
            val mMainActivity =  activity as MainActivity
            mMainActivity.setOnKeyBackPressedListener(null)
            mMainActivity.onBackPressed()
        }
    }

    override fun onAttach(context: Context?) {  //
        super.onAttach(context)
        var MainActivity =  activity as MainActivity
        MainActivity.setOnKeyBackPressedListener(this)
    }


    // 구버전을우한 키고클떄 싱크 맞추는 기능
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