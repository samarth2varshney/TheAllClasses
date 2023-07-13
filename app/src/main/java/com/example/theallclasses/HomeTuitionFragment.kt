package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.theallclasses.databinding.FragmentHomeTuitionBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
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

        binding.tvBanner.text = SharedData.HomeTuitionData!!["banner"].toString()

        //Automatic Slider
        val imageurl = SharedData.HomeTuitionData!!["slider1"] as Map<String, Any>
        sliderView = binding.imageSliderHomeTuition
        sliderAdapter = SliderAdapter(imageurl.keys.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        val imageurl2 = SharedData.HomeTuitionData!!["slider2"] as Map<String, Any>
        sliderView = binding.imageSliderHomeTuition2
        sliderAdapter = SliderAdapter(imageurl2.keys.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        lifecycle.addObserver(binding.youtubePlayerViewHometuition)
        binding.youtubePlayerViewHometuition.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = SharedData.HomeTuitionData!!["exploreHomeTuitionVideo"].toString()
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

        lifecycle.addObserver(binding.studentsExperience)
        binding.studentsExperience.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = SharedData.HomeTuitionData!!["studentExperienceVideo"].toString()
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

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


}