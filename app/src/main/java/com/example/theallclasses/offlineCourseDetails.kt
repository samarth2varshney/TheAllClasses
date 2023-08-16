package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.theallclasses.Adapters.SliderAdapter
import com.example.theallclasses.databinding.FragmentOfflineCourseDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.smarteist.autoimageslider.SliderView

class offlineCourseDetails : Fragment() {

    lateinit var binding: FragmentOfflineCourseDetailsBinding
    lateinit var map:MutableMap<String,Any>
    var location:String = ""
    var type:String = ""
    var containerId = 0
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            map = (it.getSerializable("map") as? MutableMap<String, Any>)!!
            location = it.getString("location").toString()
            type = it.getString("type").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerId = container?.id!!
        binding = FragmentOfflineCourseDetailsBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bannermap = map["bannerAndSlider"] as Map<String, Any>

        val imagemap = bannermap!!["slider1"]  as Map<String, Any>
        val imagemap2 = bannermap!!["slider2"]  as Map<String, Any>
        val cousercost = map["cost"].toString()
        val name = map["name"].toString()

        intializeslider(imagemap,binding.Slider1)
        intializeslider(imagemap2,binding.Slider2)

        Glide.with(this).load(bannermap!!["image1"]).fitCenter().into(binding.image1)
        Glide.with(this).load(bannermap!!["image2"]).fitCenter().into(binding.image2)
        Glide.with(this).load(bannermap!!["image3"]).fitCenter().into(binding.image3)
        Glide.with(this).load(bannermap!!["addressImage"]).fitCenter().into(binding.address)

        binding.address.setOnClickListener {
            val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(bannermap!!["addressLink"].toString()))
            startActivity(intent3)
        }

        intializeyoutube(bannermap!!["video1"].toString(),binding.video1)
        intializeyoutube(bannermap!!["video2"].toString(),binding.video2)

        binding.address.setOnClickListener {
            val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(map!!["addressLink"].toString()))
            startActivity(intent3)
        }

        binding.button14.setOnClickListener {
            val intent = Intent(requireContext(), PurchaseActivity::class.java)
            if (auth.currentUser != null && cousercost!="null"){
                intent.putExtra("courseName" ,name)
                intent.putExtra("cost", cousercost.toInt())
                intent.putExtra("location",location)
                intent.putExtra("type",type)
                intent.putExtra("startDate",map["startDate"].toString())
                intent.putExtra("endDate",map["endDate"].toString())
                startActivity(intent)
            }
            else{
                val intent1 = Intent(requireContext(), SignInActivity::class.java)
                startActivity(intent1)
            }
        }

        contactInfo()

    }

    private fun contactInfo() {
        if(map!!["contactInfo"] != null) {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data =
                Uri.parse("tel:${(map!!["contactInfo"] as Map<String, String>)["phoneNo"]}")
            binding.customercare5.append("${(map!!["contactInfo"] as Map<String, String>)["phoneNo"]}\n")
            binding.customercare5.append((map!!["contactInfo"] as Map<String, String>)["address"])
            binding.button2.setOnClickListener {
                startActivity(dialIntent)
            }
        }else if(SharedData.customerCare!=null){
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
        var sliderView: SliderView = ModeSlider
        var sliderAdapter = SliderAdapter(imagesMap.keys.toTypedArray())
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