package com.example.theallclasses

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.FragmentHomeBinding
import com.example.theallclasses.databinding.FragmentLiveBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.smarteist.autoimageslider.SliderView

class LiveFragment : Fragment() {

    private lateinit var binding: FragmentLiveBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLiveBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Automatic Slider
        sliderView = binding.imageSliderLive
        sliderAdapter = SliderAdapter(SharedData.imagename)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        val youtubelink = "e7RvFZ6XtkI"

        lifecycle.addObserver(binding.youtubePlayerViewMaterial)

        binding.youtubePlayerViewMaterial.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = youtubelink.toString()
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })


//        binding.btn.setOnClickListener{
//            startActivity(
//                Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("http://www.youtube.com/watch?v=cxLG2wtE7TM")
//                )
//            )
//        }
    }
}