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
     * Called to do initial creation of a fragment. This is called after onAttach(Activity) and before
     * onCreateView(LayoutInflater, ViewGroup, Bundle).
     *
     * Note that this can be called while the fragment's activity is still in the process of being created. As such, you can not rely on
     * things like the activity's content view hierarchy being initialized at this point. If you want to do work once the activity
     * itself is created, see onActivityCreated(Bundle).
     *
     * Any restored child fragments will be created before the base Fragment.onCreate method returns.
     *
     * @param savedInstanceState Bundle: If the fragment is being re-created from a previous saved state, this is the state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = NavigationDrawerConstants.TAG_SETTINGS
    }

    /**
     * Called to have the fragment instantiate its user interface view. This is optional, and non-graphical fragments can return
     * null (which is the default implementation). This will be called between onCreate(Bundle) and onActivityCreated(Bundle).
     *
     * If you return a View from here, you will later be called in onDestroyView() when the view is being released.
     *
     * @param inflater LayoutInflater: The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container 	ViewGroup: If non-null, this is the parent view that the fragment's UI should be attached to.
     * The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
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
        val intent = Intent(this.activity, LoginActivity::class.java)
        startActivity(intent)
        this.activity?.finish()
    }

    /**
     * Shared Preference에 있는 로그인 데이터를 지울 때 사용되는 함수
     */
    private fun removeLoginData()
    {
        val pref = this.activity?.getSharedPreferences(LoginActivity.KEY_LOGIN_PREFERENCE, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pref!!.edit()

        editor.clear()
        editor.apply()
    }

    /**
     * 상태메세지를 Shared Preference에 저장함
     *
     * @param msg 저장할 상태메세지
     */
    private fun saveStatusMessage(msg : String)
    {
        val pref = this.activity?.getSharedPreferences(LoginActivity.KEY_LOGIN_PREFERENCE, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = pref!!.edit()

        editor.putString(KEY_STATUS_MESSAGE, msg)
        editor.apply()
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
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned, but before any saved
     * state has been restored in to the view. This gives subclasses a chance to initialize themselves once they know their view
     * hierarchy has been completely created. The fragment's view hierarchy is not however attached to its parent at this point.
     *
     * @param view View: The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 로그아웃버튼 클릭시 나오는 다이얼로그 설정
        // 안드로이드 특성상 Negative와 Positive의 방향이 평소 쓰는 UI와 반대임
        val builder = AlertDialog.Builder(this.activity)
        builder
                .setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setNegativeButton("네", { _, _ -> run {
                    signOut()
                }
                })
                .setPositiveButton("취소", {dialog, _ ->
                    dialog.cancel()
                })
        val signOutDialog = builder.create()

        btnSignOut.setOnClickListener {
            signOutDialog.show()
        }

        // 상태메세지 설정 버튼
        btnAssign.setOnClickListener {
            val statusMessage : String = editStatusMessage.text.toString()

            val header = this.activity?.nav_view?.getHeaderView(0)
            header?.textStatusMessage?.text = statusMessage
            saveStatusMessage(statusMessage)
            hideKeyboard()
        }

        editStatusMessage.setOnFocusChangeListener{ v, hasFocus-> run {
                if (v.id == editStatusMessage.id && !hasFocus) {
                    hideKeyboard()
                }
            }
        }
    }

}