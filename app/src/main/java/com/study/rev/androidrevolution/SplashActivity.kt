package com.study.rev.androidrevolution

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

/**
 * 메인 화면이 나오기 전 뿌려주는 스플래시 액티비티
 */
class SplashActivity : AppCompatActivity() {
    /**
     * 쓰레드를 구동하기 위한 핸들러
     */
    private var mDelayHandler: Handler? = null
    /**
     * 스플래시 액티비티의 종료 시간
     */
    private val SPLASH_DELAY: Long = 1000 //1 seconds

    /**
     * 스플래시 액티비티가 돌아가는 중에 액티비티를 로딩하기 위한 쓰레드
     */
    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen)
        setContentView(R.layout.activity_splash)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    /**
     *
     */
    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
