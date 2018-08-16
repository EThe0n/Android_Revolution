package com.study.rev.androidrevolution

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_cafe.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val FINISH_INTERVAL_TIME = 2000;
    private var  backPressedTime : Long = 0;

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

        var header = nav_view.getHeaderView(0)
        header.textStudentNumber.text = intent.getStringExtra(LoginActivity.KEY_STUDENT_NUMBER)
        header.textName.text = intent.getStringExtra(LoginActivity.KEY_NAME)

        var pref = getSharedPreferences(LoginActivity.KEY_LOGIN_PREFERENCE, Context.MODE_PRIVATE)
        header.textStatusMessage.text = pref.getString(SettingsFragment.KEY_STATUS_MESSAGE, "")

        displaySelectedFragment(HomeFragment())
    }

    private var  mOnKeyBackPressedListener:onKeyBackPressedListener? = null  // 이곳에는 CAFE fRAGMENT 가들어갈예정
    fun  setOnKeyBackPressedListener(listener:onKeyBackPressedListener?) {
        mOnKeyBackPressedListener = listener
    }
    override fun onBackPressed() {

        //var tempTime :Long= System.currentTimeMillis();
        //var intervalTime :Long = tempTime - backPressedTime;

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
        ///backPressedTime = tempTime
        //Toast.makeText(applicationContext, "한번 더 뒤로가기 누르면 꺼버린다.", Toast.LENGTH_SHORT).show()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
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
