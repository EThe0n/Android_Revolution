package com.study.rev.androidrevolution


import android.os.AsyncTask
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Json 파일 download 및 처리를 위한 class
 */
class DownloadWebpageTask : AsyncTask<String, Void, String> {
    var callback : AsyncResult

    constructor(callback : AsyncResult){
        this.callback = callback
    }

    override fun doInBackground(vararg urls: String): String {
        try{
            return downloadUrl(urls[0])
        }catch(e: IOException){
            return "Failed"
        }
    }

    /**
     * Json 파일에서 실제로 사용될 string을 추출
     * @param result String
     */
    override fun onPostExecute(result : String){
        val start : Int = result.indexOf("{", result.indexOf("{") + 1)
        val end : Int = result.lastIndexOf("}")

        val jsonResponse : String = result.substring(start, end)
        try{
            val table : JSONObject = JSONObject(jsonResponse)
            callback.onResult(table)
        }catch (e: JSONException){
            e.printStackTrace()
        }
    }


    /**
     * 주어진 url로부터 data를 download
     * @param urlString String
     */
    @Throws(IOException::class)
    fun downloadUrl(urlString : String) : String{
        var ins : InputStream ?= null

        try{
            val url : URL = URL(urlString)
            val conn : HttpURLConnection = url.openConnection() as HttpURLConnection

            conn.readTimeout = 10000
            conn.connectTimeout = 15000
            conn.requestMethod = "GET"
            conn.doInput = true

            conn.connect()
            var responseCode = conn.getResponseCode()
            ins = conn.getInputStream()

            val contentAsString : String = convertStreamToString(ins)
            return contentAsString
        }finally {
            if(ins != null)
                ins.close()
        }
    }

    fun convertStreamToString(ins : InputStream) : String{
        val reader : BufferedReader = BufferedReader(InputStreamReader(ins))
        val sb : StringBuilder = StringBuilder()

        var line : String ?= null

        try{
            while(true) {
                line = reader.readLine()
                if(line == null)
                    break;

                sb.append(line + "\n")
            }
        }catch(e: IOException){
            e.printStackTrace()
        }finally {
            try{
                ins.close()
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
        return sb.toString()
    }
}