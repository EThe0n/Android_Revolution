package com.study.rev.androidrevolution

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val MusicNotificationID : Int = 1

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
            musicNotification(true)
        }

        btnStop.setOnClickListener {
            var intent = Intent(this, MusicService::class.java)
            intent.putExtra("isPlay", false)
            startService(intent)
            musicNotification(false)
        }

        btnExit.setOnClickListener{
            exitDialog()
        }
    }

    private fun musicNotification(isPlay : Boolean)
    {
        var manager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "my_channel_id_01"
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        var intent = Intent(this,MainActivity::class.java)
        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (isPlay) {
            notificationBuilder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("유희왕 오프닝")
                    .setContentText("음악 재생 중")
                    .setAutoCancel(false)
                    .setSound(null)
                    .setVibrate(null)
                    .setContentIntent(pendingIntent).build()
            notificationBuilder.setDefaults(0)

            manager.notify(MusicNotificationID, notificationBuilder.build())
        }
        else {
            manager.cancel(MusicNotificationID)
        }
    }

    private fun exitDialog()
    {
        var builder : AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle("영락없이")
        builder.setMessage("bang bang?")
        builder.setPositiveButton("BAAAM", { dialog, whichButton ->
            finish()
            System.exit(0)
        })
        builder.setNegativeButton("반동이다", {dialog, whichButton -> })

        builder.show()
    }

}
