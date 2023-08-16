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
import com.example.theallclasses.databinding.FragmentHomeTuitionBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.smarteist.autoimageslider.SliderView

class HomeTuitionFragment : Fragment() {

    private lateinit var binding: FragmentHomeTuitionBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    var containerId:Int = 0
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerId = container?.id!!
        binding = FragmentHomeTuitionBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(SharedData.HomeTuitionData == null) {
            val HomeTution = db.document("/Home_Tuition/Home_Tuition")
            HomeTution.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.HomeTuitionData = document.data as Map<String, Any>
                        intializeviews()
                    }
                }
        }else{
            intializeviews()
        }

    }

    private fun intializeviews() {

        binding.FindingTuttorbutton.setOnClickListener {
            val fragment = WebviewFragment()
            val args = Bundle()
            args.putString("formlink",SharedData.HomeTuitionData!!["findingTutorForm"].toString())
            fragment.arguments = args

            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.JoinasTutorbutton.setOnClickListener {
            val fragment = joinTutor()
            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val imageMap = SharedData.HomeTuitionData!!["slider1"] as Map<String, Any>
        val imageMap2 = SharedData.HomeTuitionData!!["slider2"] as Map<String, Any>

        intializeslider(imageMap,binding.imageSliderHomeTuition)
        intializeslider(imageMap2,binding.Slider1)

        intializeyoutube(SharedData.HomeTuitionData!!["video1"].toString(),binding.video1)
        intializeyoutube(SharedData.HomeTuitionData!!["video2"].toString(),binding.video2)

        Glide.with(this).load(SharedData.HomeTuitionData!!["image1"]).fitCenter().into(binding.image1)
        Glide.with(this).load(SharedData.HomeTuitionData!!["image2"]).fitCenter().into(binding.image2)
        Glide.with(this).load(SharedData.HomeTuitionData!!["image3"]).fitCenter().into(binding.image3)

        if(SharedData.customerCare!=null){
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data =
                Uri.parse("tel:${SharedData.customerCare!!["homeTuitionNumber"]}")
            binding.customercare5.append("${SharedData.customerCare!!["homeTuitionNumber"]} \n")
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