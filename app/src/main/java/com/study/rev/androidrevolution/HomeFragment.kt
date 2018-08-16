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

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = NavigationDrawerConstants.TAG_HOME
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun copyToClipboard(str : String) {
        var clipBoardManager : ClipboardManager = this.activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clipData : ClipData = ClipData.newPlainText("Data", str)

        clipBoardManager.primaryClip = clipData
        Toast.makeText(activity?.applicationContext, "클립보드에 복사하였습니다.", Toast.LENGTH_SHORT)
                .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textRoadNameAddress.setOnClickListener {
            copyToClipboard(resources.getString(R.string.club_road_name_address))
        }

        textAddress.setOnClickListener {
            copyToClipboard(resources.getString(R.string.club_address))
        }

        map_link.setOnClickListener{
            var intent : Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://map.naver.com/?mapmode=0&lng=5d89ecc27c18e665ad945318c3a99d80&pinId=16338555&lat=27575ffaa043999863beec7db800f450&dlevel=12&enc=b64&pinType=site"))
            startActivity(intent)
        }
    }
}