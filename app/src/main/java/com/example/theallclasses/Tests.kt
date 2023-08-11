package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.theallclasses.databinding.FragmentTestsBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.io.Serializable

class Tests : Fragment() {

    lateinit var map: Map<String, Any>
    lateinit var binding: FragmentTestsBinding
    var containerId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            map = (it.getSerializable("map") as? MutableMap<String, Any>)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestsBinding.inflate(layoutInflater)
        containerId = container?.id!!
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(map["banner1"].toString()).fitCenter().into(binding.imageView4)
        Glide.with(this).load(map["banner2"].toString()).fitCenter().into(binding.imageView7)

        val youtubelink = map["testVideo"]
        lifecycle.addObserver(binding.Video)
        binding.Video.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = youtubelink.toString()
                youTubePlayer.cueVideo(videoId, 0f)
                youTubePlayer.mute()
            }
        })

        binding.button8.setOnClickListener {
            val fragment = showTest()
            val args = Bundle()
            args.putString("name","Our Tests")
            args.putSerializable("map", map!!["ourTest"] as Map<String,Any> as Serializable)
            fragment.arguments = args
            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.button10.setOnClickListener {
            val fragment = showTest()
            val args = Bundle()
            args.putString("name","Test Series Partner")
            args.putSerializable("map", map!!["testSeriesPartner"] as Map<String,Any> as Serializable)
            fragment.arguments = args
            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

}