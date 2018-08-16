package com.study.rev.androidrevolution

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 메인 액티비티에서 처음 나오는 홈 프래그먼트
 * 주로 소개글과 동아리 주소등을 보여준다.
 */
class HomeFragment : Fragment() {
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
        activity?.title = NavigationDrawerConstants.TAG_HOME
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    /**
     * 문자열을 클립보드에 복사하는 함수
     * 수행 후 "클립보드에 복사하였습니다"라는 토스트 알림을 출력한다.
     * @param str 클립보드에 복사할 문자열
     */
    private fun copyToClipboard(str : String) {
        val clipBoardManager : ClipboardManager = this.activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData : ClipData = ClipData.newPlainText("Data", str)

        clipBoardManager.primaryClip = clipData
        Toast.makeText(activity?.applicationContext, "클립보드에 복사하였습니다.", Toast.LENGTH_SHORT)
                .show()
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

        textRoadNameAddress.setOnClickListener {
            copyToClipboard(resources.getString(R.string.club_road_name_address))
        }

        textAddress.setOnClickListener {
            copyToClipboard(resources.getString(R.string.club_address))
        }

        map_link.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(resources.getString(R.string.map_link)))
            startActivity(intent)
        }
    }
}