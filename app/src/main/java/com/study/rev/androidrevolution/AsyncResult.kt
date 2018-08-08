package com.study.rev.androidrevolution

import org.json.JSONObject

interface AsyncResult{
    fun onResult(obj : JSONObject)
}