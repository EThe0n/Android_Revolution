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

            /*
            conn.setReadTimeout(10000)
            conn.setConnectTimeout(15000)
            conn.setRequestMethod("GET")
            conn.setDoInput(true)
            */

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