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
import com.example.theallclasses.databinding.FragmentOfflineModeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.smarteist.autoimageslider.SliderView
import java.io.Serializable

class OfflineMode : Fragment() {
    private lateinit var binding: FragmentOfflineModeBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    val db = Firebase.firestore
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
                        intializeviews()
                    }
                }
        }
        else{
            intializeviews()
        }

        var centre:String
        binding.newDelhiButton.setOnClickListener {
            centre = "newDelhi"
            openFragment(centre)
        }
        binding.noidaButton.setOnClickListener {
            centre = "noida"
            openFragment(centre)
        }
        binding.ghaziabadButton.setOnClickListener {
            centre = "ghaziabad"
            openFragment(centre)
        }

    }

    private fun openFragment(centre: String) {
        val fragment = ShowCourses()
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

    private fun intializeviews() {

        val imagemap = SharedData.OfflineModeData!!["slider1"]  as Map<String, Any>
        val slideimages = imagemap.keys.toTypedArray()
        val centreImages = SharedData.OfflineModeData!!["centreImages"] as Map<String,Any>

        Glide.with(this).load(centreImages["newDelhi"]).fitCenter().into(binding.newDelhiButton)
        Glide.with(this).load(centreImages["ghaziabad"]).fitCenter().into(binding.ghaziabadButton)
        Glide.with(this).load(centreImages["noida"]).fitCenter().into(binding.noidaButton)

        //Automatic Slider
        sliderView = binding.offlineModeSlider
        sliderAdapter = SliderAdapter(slideimages) //send the array of image
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        val imagemap2 = SharedData.OfflineModeData!!["slider2"]  as Map<String, Any>
        val slideimages2 = imagemap2.keys.toTypedArray()

        sliderView = binding.offlineModeSlider2
        sliderAdapter = SliderAdapter(slideimages2) //send the array of image
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        val youtubelink = SharedData.OfflineModeData!!["exploreTheCentreVideo"].toString()
        lifecycle.addObserver(binding.youtubePlayerView2)

        binding.youtubePlayerView2.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(youtubelink, 0f)
            }
        })

        val youtubelink2 = SharedData.OfflineModeData!!["studentExperienceVideo"].toString()
        lifecycle.addObserver(binding.youtubePlayerView3)

        binding.youtubePlayerView3.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(youtubelink2, 0f)
            }
        })

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

}