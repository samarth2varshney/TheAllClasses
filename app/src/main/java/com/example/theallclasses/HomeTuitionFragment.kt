package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val HomeTution = db.document("/AppHomeTuttion/HomeTuttion")
        HomeTution.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    SharedData.HomeTuitionFragmentData = document.data as Map<String, Any>
                    openActivity()
                }
            }

    }

    private fun openActivity() {
        binding.FindingTuttorbutton.setOnClickListener {
            val fragment = WebviewFragment()
            val args = Bundle()
            args.putString("formlink",SharedData.HomeTuitionFragmentData!!["findingTuttorFrom"].toString())
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

        binding.tvBanner.text = SharedData.HomeTuitionFragmentData!!["banner"].toString()

        //Automatic Slider
        val imageurl = SharedData.HomeTuitionFragmentData!!["slider"] as Map<String, Any>
        sliderView = binding.imageSliderHomeTuition
        sliderAdapter = SliderAdapter(imageurl.keys.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        val imageurl2 = SharedData.HomeTuitionFragmentData!!["slider2"] as Map<String, Any>
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
                val videoId = SharedData.HomeTuitionFragmentData!!["exporeHomeTuition"].toString()
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

        lifecycle.addObserver(binding.studentsExperience)
        binding.studentsExperience.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = SharedData.HomeTuitionFragmentData!!["studentExperience"].toString()
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }


}