package com.example.theallclasses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.theallclasses.Adapters.SliderAdapter
import com.example.theallclasses.Adapters.auth
import com.example.theallclasses.databinding.FragmentHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    var containerId:Int = 0
    val db = Firebase.firestore
    private var set = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerId = container?.id!!
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.IO) {
            val customerCareNumber = db.document("/customerCareNumber/numbers")
            customerCareNumber.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.customerCare = document.data as Map<String, Any>
                    }
                }
        }

        GlobalScope.launch (Dispatchers.IO) {
            val customerCareNumber = db.document("/Homepage_button_icons/buttonIcon")
            customerCareNumber.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.icons = document.data as Map<String, Any>
                        putbuttonicon("class11&12Icon",binding.cbseButton)
                        putbuttonicon("governmentjobexamIcon",binding.jeeAdvancedButton)
                        putbuttonicon("jeeIcon",binding.jeeMainsButton)
                        putbuttonicon("neetIcon",binding.neetUgButton)
                        putbuttonicon("otherIcon",binding.othersButton)
                        putbuttonicon("perfoundationIcon",binding.foundationButton)
                    }
                }
        }

        binding.textView7.setOnClickListener{
            openShowMaterial(SharedData.MaterialFragmentData!!["booklets"] as Map<String,Any>)
        }

        binding.textView18.setOnClickListener {
            val fragment = OfflineMode()
            val fragmentManager: FragmentManager =
                (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.textView19.setOnClickListener {
            val fragment = HomeTuitionFragment()
            val fragmentManager: FragmentManager =
                (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.jeeAdvancedButton.setOnClickListener{
            openShowCourses(SharedData.JEE_Advanced,"jeeadvance")
        }
        binding.jeeMainsButton.setOnClickListener {
            openShowCourses(SharedData.JEEmap, "jeemains")
        }
        binding.neetUgButton.setOnClickListener {
            openShowCourses(SharedData.NEETmap, "neet")
        }
        binding.cbseButton.setOnClickListener {
            openShowCourses(SharedData.Boardmap, "cbse")
        }
        binding.othersButton.setOnClickListener {
            openShowCourses(SharedData.others,"others")
        }
        binding.foundationButton.setOnClickListener {
            openShowCourses(SharedData.foundation,"foundation")
        }

        binding.offlineButton.setOnClickListener {
            val fragment = OfflineMode()
            val fragmentManager: FragmentManager =
                (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.button3.setOnClickListener {
            val fragment = HomeTuitionFragment()
            val fragmentManager: FragmentManager =
                (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        intializeslider(binding.imageSlider,"homePageSlider1")
        intializeslider(binding.imageSlider2,"offlineCenterSlider")
        intializeslider(binding.imageSlider3,"homeTutionSlider")
        intializeslider(binding.imageSlider4,"successfulStudentSlider")
        intializeslider(binding.imageSlider5,"studentSlider")
        intializeslider(binding.imageSlider6,"facultiesSlider")

        intializeyoutubeplayer(binding.bookletVideo,"bookletVideo")
        intializeyoutubeplayer(binding.onlineSystematicContentVideo,"onlineSystematicContentVideo")
        intializeyoutubeplayer(binding.offlinecentervideo,"offlineCenterVideo")
        intializeyoutubeplayer(binding.hometutionvideo,"homeTutionVidoe")
        intializeyoutubeplayer(binding.successfulStudentvideo,"successfulStudentVideo")
        intializeyoutubeplayer(binding.studentVideo,"studentVideo")

        setImages("onlineImage",binding.onlineImage)
        setImages("indiaFirstImage",binding.indiaFirst)
        setImages("delhiNcrImage",binding.delhiNcr)
        setImages("trainedHomeTutorImage",binding.trainedHometutor)
        setImages("dreamTurnQuote",binding.dreamTurnQuote)
        setImages("memberofDreamImage",binding.memberOfDream)
        setImages("freeContentImage",binding.freeContent)
        setImages("bookFlashImage",binding.bookImage)
        setImages("TrainedAndExpertFacultiesImage",binding.TrainedAndExportFaculties)
        setImages("bottomImage",binding.bottomImage)
    }

    private fun putbuttonicon(s: String, Imageview: ImageView) {
        if(SharedData.icons!![s]!=null)
        Glide.with(requireContext()).load(SharedData.icons!![s]).fitCenter().into(Imageview)
    }

    private fun setImages(s: String, Imageview: ImageView) {
        Glide.with(requireContext()).load(SharedData.HomeFragmentData!![s]).fitCenter().into(Imageview)
    }

    fun intializeyoutubeplayer(VideoPlayer: YouTubePlayerView, s: String) {
        val youtubelink = SharedData.HomeFragmentData!![s]
        lifecycle.addObserver(VideoPlayer)
        VideoPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = youtubelink.toString()
                youTubePlayer.cueVideo(videoId, 0f)
                youTubePlayer.mute()
            }
        })
    }

    private fun intializeslider(imageSlider: SliderView, s: String) {
        sliderView = imageSlider
        sliderAdapter = SliderAdapter((SharedData.HomeFragmentData!![s] as Map<String, Any>).keys.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }

    private fun openShowCourses(course: Map<String, Any>?, s: String) {
        val fragment = showOnlineCourses()
        val args = Bundle()
        args.putSerializable("map", course as Serializable)
        args.putBoolean("bookSeat",false)
        args.putString("location",s)
        args.putString("type","online")
        fragment.arguments = args

        val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun openShowMaterial(course: Map<String, Any>?) {
        val fragment = ShowMaterial()
        val args = Bundle()
        args.putSerializable("map", course as Serializable)
        fragment.arguments = args

        val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}