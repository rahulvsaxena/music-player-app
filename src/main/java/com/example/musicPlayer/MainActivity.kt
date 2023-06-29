package com.example.musicPlayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    //initilise the runnable variable
    private lateinit var run: Runnable
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //using handle
        handler = Handler(Looper.getMainLooper())
        val playButton = findViewById<FloatingActionButton>(R.id.fabPlay)
        playButton.setOnClickListener {
            //creating object if doesnt exists
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.video)
                initializeTimer()
            }
            mediaPlayer?.start()
        }

        val pauseButton = findViewById<FloatingActionButton>(R.id.fabPause)
        pauseButton.setOnClickListener {
            mediaPlayer?.pause()
        }

        val stopButton = findViewById<FloatingActionButton>(R.id.fabStop)
        stopButton.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            //removing runnable
            handler.removeCallbacks(run)

        }
    }
    private fun initializeTimer() {
        val tvPlayed = findViewById<TextView>(R.id.tvPlayed)
        val tvDue = findViewById<TextView>(R.id.tvDue)
        //allows running block over and over again
        //runnable is handled by handler
        run = Runnable {
            val playedTime = mediaPlayer!!.currentPosition / 1000
            tvPlayed.text = "$playedTime sec"
            val duration = mediaPlayer!!.duration / 1000
            val dueTime = duration - playedTime
            tvDue.text = "$dueTime sec"
            //run runnable after 1 millisecond
            handler.postDelayed(run, 1000)
        }
        handler.postDelayed(run, 1000)

    }
}