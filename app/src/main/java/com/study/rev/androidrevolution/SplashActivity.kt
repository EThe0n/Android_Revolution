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
    private lateinit var mDelayHandler: Handler
    /**
     * 스플래시 액티비티의 종료 시간
     */
    private val splashDelay: Long = 1000 //1 seconds

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
     * Called when the activity is starting. This is where most initialization should go: calling setContentView(int) to inflate
     * the activity's UI, using findViewById(int) to programmatically interact with widgets in the UI,
     * calling managedQuery(android.net.Uri, String[], String, String[], String) to retrieve cursors for data being displayed, etc.
     *
     * You can call finish() from within this function, in which case onDestroy() will be immediately called after
     * onCreate(Bundle) without any of the rest of the activity lifecycle (onStart(), onResume(), onPause(), etc) executing.
     *
     * Derived classes must call through to the super class's implementation of this method. If they do not, an exception will be thrown.
     * This method must be called from the main thread of your app.
     * If you override this method you must call through to the superclass implementation.
     *
     * @param savedInstanceState Bundle: If the activity is being re-initialized after previously being shut down then this Bundle
     * contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_NoTitleBar_Fullscreen)
        setContentView(R.layout.activity_splash)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler.postDelayed(mRunnable, splashDelay)
    }

    /**
     * Perform any final cleanup before an activity is destroyed. This can happen either because the activity is finishing
     * (someone called finish() on it, or because the system is temporarily destroying this instance of the activity to save space.
     * You can distinguish between these two scenarios with the isFinishing() method.
     *
     * Note: do not count on this method being called as a place for saving data! For example, if an activity is editing data in a content provider,
     * those edits should be committed in either onPause() or onSaveInstanceState(Bundle), not here.
     * This method is usually implemented to free resources like threads that are associated with an activity, so that a destroyed activity does not
     * leave such things around while the rest of its application is still running. There are situations where the system will simply kill
     * the activity's hosting process without calling this method (or any others) in it, so it should not be used to do things that are intended to remain around after the process goes away.
     * Derived classes must call through to the super class's implementation of this method. If they do not, an exception will be thrown.
     * If you override this method you must call through to the superclass implementation.
     */
    public override fun onDestroy() {
        mDelayHandler.removeCallbacks(mRunnable)
        super.onDestroy()
    }
}
