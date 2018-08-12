package com.study.rev.androidrevolution

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import kotlinx.android.synthetic.main.fragment_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ListFragment : Fragment(){

    var listSet : ArrayList<ListColumn> = ArrayList<ListColumn>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = NavigationDrawerConstants.TAG_LIST

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_btnSearch.setOnClickListener { View -> buttonClickHandler(view)}
        layout_btnSearch.callOnClick()

        var connMgr : ConnectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo : NetworkInfo = connMgr.activeNetworkInfo

        if(networkInfo != null && networkInfo.isConnected){
            layout_btnSearch.setEnabled(true)
        }
        else{
            layout_btnSearch.setEnabled(false)
        }
    }

    fun buttonClickHandler(view : View){

        listSet.clear()

        DownloadWebpageTask(object : AsyncResult{
            override fun onResult(obj: JSONObject) {
                processJson(obj)
            }
        }).execute("https://spreadsheets.google.com/tq?key=1wFrFaxr6_cn0W4H2XJESPsw2jA5eGhQTtLR2xWaeykg")
                //.execute("https://spreadsheets.google.com/tq?key=1rKtgNrlWUJSR9uuC7w_XsgmlB8P6w9EdYBpfVKPdRR0")
    }


    fun processJson(obj : JSONObject){
        try{
            var rows : JSONArray = obj.getJSONArray("rows")

            for(r in 0 until rows.length()){
                var row : JSONObject = rows.getJSONObject(r)
                var col : JSONArray = row.getJSONArray("c")

                var name : String = col.getJSONObject(0).getString("v")
                var subject : String = col.getJSONObject(1).getString("v")
                var borrower : String = nullToEmpty(col.getJSONObject(2)?.getString("v"))

                var item : ListColumn = ListColumn(name, subject, borrower)
                listSet.add(item)
            }

            var adapter : ListAdapter = ListAdapter(this.context, R.layout.list_column, listSet)
            layout_listview.adapter = adapter

        }catch (e : JSONException){
            e.printStackTrace()
        }
    }


    fun nullToEmpty(ins : String?) : String{
        if(ins != "null") {
            return (ins as String)
        }
        return ""
    }
}