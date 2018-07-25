package com.study.rev.androidrevolution

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {
    var player : MediaPlayer = MediaPlayer()

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var isPlay = intent!!.getBooleanExtra("isPlay", false)

        if (isPlay) {
            if (player.isPlaying) {
                player.release()
            }
            player = MediaPlayer.create(this, R.raw.yugioh)
            player.start()
        }
        else {
            player.stop()
        }

        return START_NOT_STICKY
    }
}
