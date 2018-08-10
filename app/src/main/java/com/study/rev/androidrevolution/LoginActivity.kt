package com.study.rev.androidrevolution

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    companion object {
        val KEY_NAME : String = "NAME"
        val KEY_STUDENT_NUMBER : String = "STUDENT_NUMBER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener {
            var studentNumber : String = editStudentNumber.text.toString()
            var name : String = editName.text.toString()

            if (studentNumber.isEmpty() || name.isEmpty()) {
                Toast.makeText(applicationContext, R.string.warning_login_information_empty, Toast.LENGTH_SHORT)
                        .show()
            }
            else {
                var loginInfo = Intent(this, MainActivity::class.java)

                loginInfo.putExtra(KEY_NAME, studentNumber)
                loginInfo.putExtra(KEY_STUDENT_NUMBER, name)

                startActivity(loginInfo)
                finish()
            }
        }
    }
}
