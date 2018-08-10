package com.study.rev.androidrevolution

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = NavigationDrawerConstants.TAG_SETTINGS
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    private fun signOut()
    {
        removeLoginData()
        var intent = Intent(this.activity, LoginActivity::class.java)
        startActivity(intent)
        this.activity?.finish()
    }

    private fun removeLoginData()
    {
        var pref = this.activity?.getSharedPreferences(LoginActivity.KEY_LOGIN_PREFERENCE, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = pref!!.edit()

        editor.clear()
        editor.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignOut.setOnClickListener {
            signOut()
        }
    }
}