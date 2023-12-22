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
import com.example.theallclasses.databinding.FragmentOfflineModeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.smarteist.autoimageslider.SliderView
import java.io.Serializable

class OfflineMode : Fragment() {
    private lateinit var binding: FragmentOfflineModeBinding
    val db = Firebase.firestore
    var containerId:Int = 0
    var dataLoaded = false

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
        binding = FragmentOfflineModeBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(SharedData.OfflineModeData==null) {
            val Board = db.document("/Offline/Offline")
            Board.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.OfflineModeData = document.data as MutableMap<String, Any>
                        dataLoaded = true
                        intializeviews()
                    }
                }
        }
        else{
            dataLoaded = true
            intializeviews()
        }

        var centre:String
        binding.newDelhiButton.setOnClickListener {
            centre = "newDelhi"
            if(dataLoaded) openFragment(centre)
        }
        binding.noidaButton.setOnClickListener {
            centre = "noida"
            if(dataLoaded) openFragment(centre)
        }
        binding.ghaziabadButton.setOnClickListener {
            centre = "ghaziabad"
            if(dataLoaded) openFragment(centre)
        }
        binding.gurgaonButton.setOnClickListener {
            centre = "gurgaon"
            if(dataLoaded) openFragment(centre)
        }

    }

    private fun intializeviews() {

        val imagemap = SharedData.OfflineModeData!!["slider1"]  as Map<String, Any>
        val imagemap2 = SharedData.OfflineModeData!!["slider2"]  as Map<String, Any>

        val centreImages = SharedData.OfflineModeData!!["centreImages"] as Map<String,Any>

        Glide.with(this).load(centreImages["newDelhi"]).fitCenter().into(binding.newDelhiButton)
        Glide.with(this).load(centreImages["ghaziabad"]).fitCenter().into(binding.ghaziabadButton)
        Glide.with(this).load(centreImages["noida"]).fitCenter().into(binding.noidaButton)
        Glide.with(this).load(centreImages["gurgaon"]).fitCenter().into(binding.gurgaonButton)

        Glide.with(this).load(SharedData.OfflineModeData!!["image1"]).fitCenter().into(binding.image1)
        Glide.with(this).load(SharedData.OfflineModeData!!["image2"]).fitCenter().into(binding.image2)
        Glide.with(this).load(SharedData.OfflineModeData!!["image3"]).fitCenter().into(binding.image3)

        intializeyoutube(SharedData.OfflineModeData!!["video1"].toString(),binding.video1)
        intializeyoutube(SharedData.OfflineModeData!!["video2"].toString(),binding.video2)

        intializeslider(imagemap,binding.offlineModeSlider)
        intializeslider(imagemap2,binding.Slider1)

        if(SharedData.customerCare!=null){
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data =
                Uri.parse("tel:${SharedData.customerCare!!["homeTuitionNumber"]}")
            binding.customercare4.append("${SharedData.customerCare!!["homeTuitionNumber"]} \n")
            binding.button2.setOnClickListener {
                startActivity(dialIntent)
            }
        }

    }

    private fun openFragment(centre: String) {
        val fragment = showOfflineCourses()
        val args = Bundle()
        args.putSerializable("map", SharedData.OfflineModeData?.get(centre) as Serializable)
        args.putBoolean("bookSeat",true)
        args.putString("location",centre)
        args.putString("type","offline")
        fragment.arguments = args

        val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
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