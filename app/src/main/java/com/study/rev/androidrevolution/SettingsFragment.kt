package com.study.rev.androidrevolution

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import android.view.inputmethod.InputMethodManager

/**
 * 설정을 담당하는 프래그먼트
 * 주로 상태메세지 설정 및 로그아웃 기능을 담당하고 있다.
 *
 * @constructor Default constructor
 */
class SettingsFragment : Fragment() {

    companion object {
        /**
         * Shared Preference에 있는 상태 메세지 정보를 가져오기 위한 키
         */
        const val KEY_STATUS_MESSAGE : String = "STATUS_MESSAGE"
    }

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = NavigationDrawerConstants.TAG_SETTINGS
    }

    /**
     *
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    /**
     * 로그아웃 시 실행되는 함수
     * 해당 프래그먼트가 있는 액티비티를 종료하고 로그인 액티비티로 이동한다
     */
    private fun signOut()
    {
        removeLoginData()
        var intent = Intent(this.activity, LoginActivity::class.java)
        startActivity(intent)
        this.activity?.finish()
    }

    /**
     * Shared Preference에 있는 로그인 데이터를 지울 때 사용되는 함수
     */
    private fun removeLoginData()
    {
        var pref = this.activity?.getSharedPreferences(LoginActivity.KEY_LOGIN_PREFERENCE, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = pref!!.edit()

        editor.clear()
        editor.commit()
    }

    /**
     * 상태메세지를 Shared Preference에 저장함
     *
     * @param msg 저장할 상태메세지
     */
    private fun saveStatusMessage(msg : String)
    {
        var pref = this.activity?.getSharedPreferences(LoginActivity.KEY_LOGIN_PREFERENCE, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = pref!!.edit()

        editor.putString(KEY_STATUS_MESSAGE, msg)
        editor.commit()
    }

    /**
     * 입력시에 나온 키보드를 내리는 함수
     */
    private fun hideKeyboard()
    {
        try {
            val imm = this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        catch (e : Exception) {
        }
    }

    /**
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var builder = AlertDialog.Builder(this.activity)
        builder
                .setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setNegativeButton("네", { dialog, id -> run {
                    signOut()
                }
                })
                .setPositiveButton("취소", {dialog, id ->
                    dialog.cancel()
                })
        var signOutDialog = builder.create()

        btnSignOut.setOnClickListener {
            signOutDialog.show()
        }

        btnAssign.setOnClickListener {
            var statusMessage : String = editStatusMessage.text.toString()

            var header = this.activity?.nav_view?.getHeaderView(0)
            header?.textStatusMessage?.text = statusMessage
            saveStatusMessage(statusMessage)
            hideKeyboard()
        }

        editStatusMessage.setOnFocusChangeListener{ view, hasFocus-> run {
                if (view.id == editStatusMessage.id && !hasFocus) {
                    hideKeyboard()
                }
            }
        }
    }

}