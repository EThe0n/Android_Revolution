package com.study.rev.androidrevolution

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val id = intent.getStringExtra("ID")
        val pw = intent.getStringExtra("PW")

        t1.text = "당신의 ID는 ${id}입니다."
        t2.text = "비밀번호는 ${pw}군요"

        btnPlay.setOnClickListener{
            var intent = Intent(this, MusicService::class.java)
            intent.putExtra("isPlay", true)
            startService(intent)
        }

        btnStop.setOnClickListener {
            var intent = Intent(this, MusicService::class.java)
            intent.putExtra("isPlay", false)
            startService(intent)
        }
    }
}
