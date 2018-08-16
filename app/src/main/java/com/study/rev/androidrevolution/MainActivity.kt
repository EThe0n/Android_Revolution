package com.study.rev.androidrevolution

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(R.id.nav_home)

        val header = nav_view.getHeaderView(0)
        header.textStudentNumber.text = intent.getStringExtra(LoginActivity.KEY_STUDENT_NUMBER)
        header.textName.text = intent.getStringExtra(LoginActivity.KEY_NAME)

        val pref = getSharedPreferences(LoginActivity.KEY_LOGIN_PREFERENCE, Context.MODE_PRIVATE)
        header.textStatusMessage.text = pref.getString(SettingsFragment.KEY_STATUS_MESSAGE, "")

        displaySelectedFragment(HomeFragment())
    }

    /**
     * mOnKeyBackPressedListener 에 카페  프레그먼트가 들어간다.
     */
    private var  mOnKeyBackPressedListener:OnKeyBackPressedListener? = null

    /**
     * setOnKeyBackPressedListener로  cafeFragement 를 가져온다.
     */
    fun  setOnKeyBackPressedListener(listener:OnKeyBackPressedListener?) {
        mOnKeyBackPressedListener = listener
    }

    /**
     * 뒤로 가기 버튼을 눌렀을 때 실행되는 함수
     * 1. navigation drawer가 열려있는 경우에는 navigation drawer를 닫는다
     * 2. webview에서 뒤로 갈 수 있는 경우에는 webview에서 뒤로 가기를 실행한다
     * 3. 앱을 종료한다
     * 위의 순서대로 진행된다
     */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else if (mOnKeyBackPressedListener != null) {
            mOnKeyBackPressedListener?.onBack()
        }
        else
        {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> displaySelectedFragment(HomeFragment())
            R.id.nav_cafe -> displaySelectedFragment(CafeFragment())
            R.id.nav_list -> displaySelectedFragment(ListFragment())
            R.id.nav_settings -> displaySelectedFragment(SettingsFragment())
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displaySelectedFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame, fragment)
                .commit()
    }
}
