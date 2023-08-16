package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.theallclasses.Adapters.SliderAdapter
import com.example.theallclasses.databinding.FragmentJoinTutorBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.smarteist.autoimageslider.SliderView
import java.io.Serializable

class joinTutor : Fragment() {
    private lateinit var binding: FragmentJoinTutorBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    var containerId:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerId = container?.id!!
        binding = FragmentJoinTutorBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val map = SharedData.HomeTuitionData!!["joinAsTutorBannersAndVideos"] as Map<String,Any>

        val imagemap = map!!["slider1"]  as Map<String, Any>
        val imagemap2 = map!!["slider2"]  as Map<String, Any>

        intializeslider(imagemap,binding.imageSliderjoinTutor)
        intializeslider(imagemap2,binding.Slider1)

        Glide.with(this).load(map!!["image1"]).fitCenter().into(binding.image1)
        Glide.with(this).load(map!!["image2"]).fitCenter().into(binding.image2)
        Glide.with(this).load(map!!["image3"]).fitCenter().into(binding.image3)

        intializeyoutube(map!!["video1"].toString(),binding.video1)
        intializeyoutube(map!!["video2"].toString(),binding.video2)

        binding.joinTeacherbutton.setOnClickListener {
            SharedData.teacherTrainingProgram = SharedData.HomeTuitionData!!["teacherTrainingProgram"] as Map<String,Any>
            val fragment = showTutorCourses()
            val args = Bundle()
            args.putSerializable("map", SharedData.teacherTrainingProgram as Serializable)
            args.putBoolean("bookSeat",true)
            args.putString("location","teachertraining")
            args.putString("type","offline")
            fragment.arguments = args

            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.IhaveExperincebutton.setOnClickListener {
            val fragment = WebviewFragment()
            val args = Bundle()
            args.putString("formlink",SharedData.HomeTuitionData!!["joinTeacherTranningProgramForm"].toString())
            fragment.arguments = args

            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        if(SharedData.customerCare!=null){
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data =
                Uri.parse("tel:${SharedData.customerCare!!["homeTuitionNumber"]}")
            binding.customercare6.append("${SharedData.customerCare!!["homeTuitionNumber"]} \n")
            binding.button2.setOnClickListener {
                startActivity(dialIntent)
            }
        }

    }

    private fun intializeslider(imagesMap: Map<String, Any>, ModeSlider: SliderView) {
        sliderView = ModeSlider
        sliderAdapter = SliderAdapter(imagesMap.keys.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }

    private fun intializeyoutube(youtubelink: String, youtubePlayerView: YouTubePlayerView) {

        lifecycle.addObserver(youtubePlayerView)
        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(youtubelink, 0f)
                youTubePlayer.mute()
            }
        })
    }

}