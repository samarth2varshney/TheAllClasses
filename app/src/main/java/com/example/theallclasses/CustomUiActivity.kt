package com.example.theallclasses

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import java.util.*
import kotlin.concurrent.schedule

class CustomUiActivity : AppCompatActivity() {

    private var shouldUpdateSeekBar = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_ui)
        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_player_view)

        lifecycle.addObserver(youTubePlayerView)

        val customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_player_ui)
        val seekb = findViewById<SeekBar>(R.id.seekBar)
        var videoduration:Int=1
        val playerTracker: YouTubePlayerTracker = YouTubePlayerTracker()

        //val youTubePlayerSeekBar = findViewById<YouTubePlayerView>(R.id.youtube_player_seekbar)

        val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
            @SuppressLint("SuspiciousIndentation")

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                // findViewById<TextView>(R.id.textView).text = duration.toString() + ""
                videoduration = duration.toInt()
                seekb.max = videoduration
            }
            @SuppressLint("SuspiciousIndentation")
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val customPlayerUiController = CustomPlayerUiController(
                    this@CustomUiActivity,
                    customPlayerUi,
                    youTubePlayer,
                    youTubePlayerView
                )

                youTubePlayer.addListener(customPlayerUiController)
                youTubePlayerView.addFullScreenListener(customPlayerUiController)
                youTubePlayerView.exitFullScreen()
                val youtubelink = intent.getStringExtra("youtubelink")
                youTubePlayer.loadOrCueVideo(lifecycle, youtubelink!!, 0f)
                youTubePlayer.addListener(playerTracker)
                findViewById<Button>(R.id.play).setOnClickListener {
                    if (playerTracker.state == PlayerConstants.PlayerState.PLAYING)
                        youTubePlayer.pause()
                    if (playerTracker.state != PlayerConstants.PlayerState.PLAYING)
                        youTubePlayer.play()
                }
                findViewById<SeekBar>(R.id.seekBar)?.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    }

                    override fun onStartTrackingTouch(seek: SeekBar) {
                        shouldUpdateSeekBar = false
                        // write custom code for progress is started
                    }

                    override fun onStopTrackingTouch(seek: SeekBar) {
                        // write custom code for progress is stopped
                        youTubePlayer.seekTo(seek.progress.toFloat())
                        Timer().schedule(400){
                            shouldUpdateSeekBar = true
                        }
                    }
                })
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                val enterExitFullscreenButton = findViewById<Button>(R.id.forward)
                enterExitFullscreenButton.setOnClickListener {
                    youTubePlayer.seekTo(second+5)
                }
                if(shouldUpdateSeekBar)
                    seekb.setProgress((second).toInt(),true)

            }

        }

        // disable web ui
        val options = IFramePlayerOptions.Builder().controls(0).build()
        youTubePlayerView.initialize(listener, options)
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            youTubePlayerView!!.enterFullScreen()
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            youTubePlayerView!!.exitFullScreen()
//        }
//    }

}

