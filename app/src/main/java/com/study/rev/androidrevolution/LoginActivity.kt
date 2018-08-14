package com.study.rev.androidrevolution

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    companion object {
        /**
         * Shared Preference에서 이름 정보를 가져오기 위한 키
         */
        const val KEY_NAME : String = "NAME"

        /**
         * Shared Preference에서 학번 정보를 가져오기 위한 키
         */
        const val KEY_STUDENT_NUMBER : String = "STUDENT_NUMBER"

        /**
         * Shared Preference에서 로그인 정보를 가져오기 위한 키
         */
        const val KEY_LOGIN_PREFERENCE : String = "LOGIN_DATA"

        /**
         * Shared Preference에서 자동 로그인 설정 여부를 가져오기 위한 키
         */
        const val KEY_IS_AUTO_LOGIN : String = "IS_AUTO_LOGIN"
    }

    /**
     * 로그인 시에 호출되는 함수
     * 정보를 담아서 로그인 액티비티를 닫고 메인 액티비티로 이동한다.
     *
     * @param studentNumber 학번 정보
     * @param name 이름 정보
     */
    private fun login(studentNumber : String, name : String) {
        var loginInfo = Intent(this, MainActivity::class.java)

        loginInfo.putExtra(KEY_NAME, studentNumber)
        loginInfo.putExtra(KEY_STUDENT_NUMBER, name)

        startActivity(loginInfo)
        finish()
    }

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var pref : SharedPreferences = getSharedPreferences(KEY_LOGIN_PREFERENCE, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = pref.edit()

        if (pref.getBoolean(KEY_IS_AUTO_LOGIN, false)) {
            var studentNumber : String = pref.getString(KEY_STUDENT_NUMBER, "")
            var name : String = pref.getString(KEY_NAME, "")
            login(studentNumber, name)
        }

        loginButton.setOnClickListener {
            var studentNumber : String = editStudentNumber.text.toString()
            var name : String = editName.text.toString()

            if (studentNumber.isEmpty() || name.isEmpty()) {
                Toast.makeText(applicationContext, R.string.warning_login_information_empty, Toast.LENGTH_SHORT)
                        .show()
            }
            else {
                if (autoLoginCheckBox.isChecked) {
                    // Shared Preferences에 데이터 저장
                    editor.putString(KEY_NAME, name)
                    editor.putString(KEY_STUDENT_NUMBER, studentNumber)
                    editor.putBoolean(KEY_IS_AUTO_LOGIN, true)
                    editor.commit()
                }
                login(studentNumber, name)
            }
        }
    }
}
