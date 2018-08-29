package com.study.rev.androidrevolution

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * 구글 시트의 데이터를 가져와 화면에 출력해주는 fragment
 */
class ListFragment : Fragment(){

    var listSet : ArrayList<ListColumn> = ArrayList<ListColumn>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = NavigationDrawerConstants.TAG_LIST

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_list, container, false)
    }

    /**
     * Initialization을 위한 함수
     * Event listener setting
     * Network connection check
     * @param view View
     * @param savedInstanceState Bundle
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_btnSearch.setOnClickListener { View -> buttonClickHandler(view)}

        val connMgr : ConnectivityManager? = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo : NetworkInfo? = connMgr?.activeNetworkInfo

        if(networkInfo != null && networkInfo.isConnected){
            layout_btnSearch.isClickable = true
            layout_btnSearch.callOnClick()
        }
        else{
            layout_btnSearch.isClickable = false
            Toast.makeText(activity?.applicationContext, "네트워크에 연결되어있지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Button에 사용하는 Event listener
     * "https://spreadsheets.google.com/tq?key=키값" url을 통해 json 파일을 받아 처리
     * @param view View
     */
    private fun buttonClickHandler(view : View){
        layout_btnSearch.isClickable = false
        listSet.clear()
        layout_listview.adapter = null

        DownloadWebpageTask(object : AsyncResult{
            override fun onResult(obj: JSONObject) {
                processJson(obj)
            }
        }).execute("https://spreadsheets.google.com/tq?key=1rKtgNrlWUJSR9uuC7w_XsgmlB8P6w9EdYBpfVKPdRR0")


    }

    /**
     * listview에서 각 row를 누를 시 세부 정보를 띄워주는 함수
     * @param data ListColumn : row에 들어있는 data
     */
    private fun listViewClickEvent(data : ListColumn){
        var dataStr : String = "제목 : " + data.name + "\n주제 : " + data.subject + "\n대여자 : "
        if(data.borrower != ""){
            dataStr += data.borrower
        }
        else{
            dataStr += "없음"
        }
        AlertDialog.Builder(activity)
                .setTitle("세부 정보")
                .setMessage(dataStr)
                .show()
    }

    /**
     * Json 파일을 처리하는 함수
     * row col로 쪼개어 하나의 ListColumn object로 만들어 ArrayList에 추가
     * 화면에 출력하기 위한 Adapter에 access
     * listview의 row에 event listener setting
     * @param obj JSONObject
     */
    fun processJson(obj : JSONObject){
        try{
            val rows : JSONArray = obj.getJSONArray("rows")

            for(r in 0 until rows.length()){
                val row : JSONObject = rows.getJSONObject(r)
                val col : JSONArray = row.getJSONArray("c")

                val name : String = col.getJSONObject(0).getString("v")
                val subject : String = col.getJSONObject(1).getString("v")
                val borrower : String = nullToEmpty(col.getJSONObject(2)?.getString("v"))

                val item : ListColumn = ListColumn(name, subject, borrower)
                listSet.add(item)
            }

            val adapter : ListAdapter = ListAdapter(this.context, R.layout.list_column, listSet)
            layout_listview.adapter = adapter

            layout_listview.setOnItemClickListener { parent, view, position, id ->
                //Toast.makeText(activity, "Position Clicked:"+" "+position,Toast.LENGTH_SHORT).show()
                listViewClickEvent(listSet[position])
            }


        }catch (e : JSONException){
            e.printStackTrace()
        }finally {
            layout_btnSearch.isClickable = true
        }
    }

    /**
     * null을 공백으로 바꾸기 위한 함수
     * @param ins String : input string
     */
    private fun nullToEmpty(ins : String?) : String{
        if(ins != "null") {
            return (ins as String)
        }
        return ""
    }
}