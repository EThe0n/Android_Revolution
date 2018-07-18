package com.study.rev.androidrevolution

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            val loginInfo = Intent(this, MainActivity::class.java)

            loginInfo.putExtra("ID", editID.text.toString())
            loginInfo.putExtra("PW", editPW.text.toString())
            
            startActivity(loginInfo)
        }
    }
}
