package com.example.theallclasses

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.FragmentHomeBinding
import com.example.theallclasses.databinding.FragmentHomeTuitionBinding
import com.example.theallclasses.databinding.FragmentLiveBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.smarteist.autoimageslider.SliderView

class HomeTuitionFragment : Fragment() {

    private lateinit var binding: FragmentHomeTuitionBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeTuitionBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvBanner.text = SharedData.HomeTuitionFragmentData!!["banner"].toString()
        //Automatic Slider
        val imageurl = SharedData.HomeTuitionFragmentData!!["slider"] as? List<String>
        sliderView = binding.imageSliderHomeTuition
        sliderAdapter = SliderAdapter(imageurl!!.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        val youtubelink = SharedData.HomeTuitionFragmentData!!["youtube"]

        lifecycle.addObserver(binding.youtubePlayerViewHometuition)

        binding.youtubePlayerViewHometuition.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = youtubelink.toString()
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }
}