package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.theallclasses.databinding.FragmentJoinTutorBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
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

        lifecycle.addObserver(binding.youtubePlayerViewJoinTutor)
        binding.youtubePlayerViewJoinTutor.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = SharedData.HomeTuitionData!!["joinTutorVideo"].toString()
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

        val imageurl = SharedData.HomeTuitionData!!["jointutorslider"] as Map<String, Any>
        sliderView = binding.imageSliderjoinTutor
        sliderAdapter = SliderAdapter(imageurl.keys.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        binding.joinTeacherbutton.setOnClickListener {
            val fragment = ShowCourses()
            val args = Bundle()
            args.putSerializable("map", SharedData.HomeTuitionData!!["teacherTrainingProgram"] as Serializable)
            args.putBoolean("bookSeat",true)
            args.putString("location","homeTuttion")
            args.putString("type","teacherTrainingProgram")
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
            args.putString("formlink",SharedData.HomeTuitionData!!["jointutorihaveexperienceform"].toString())
            fragment.arguments = args

            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

}