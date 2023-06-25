package com.example.theallclasses

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.theallclasses.databinding.ActivityMain2Binding
import com.example.theallclasses.databinding.FragmentOfflinemode1Binding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.smarteist.autoimageslider.SliderView
import java.io.Serializable

class offlinemode1 : Fragment() {
    private lateinit var binding: FragmentOfflinemode1Binding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    var frontPageMap: Map<String, Any>? = null
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
        binding = FragmentOfflinemode1Binding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:${SharedData.customerCareNumbers!!["offlineModeNumber"].toString()}")
        binding.customercare.append(SharedData.customerCareNumbers!!["offlineModeNumber"].toString())
        binding.customercare.setOnClickListener {
            startActivity(dialIntent)
        }


        val Board = db.document("/AppOfflineMode/frontPage")
        Board.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    frontPageMap = document.data as Map<String, Any>
                    intializeviews()
                }
            }

        var centre:String
        binding.newDelhiButton.setOnClickListener {
            centre = "/AppOfflineMode/newDelhi"
            openFragment(centre)
        }
        binding.noidaButton.setOnClickListener {
            centre = "/AppOfflineMode/noida"
            openFragment(centre)
        }
        binding.ghaziabadButton.setOnClickListener {
            centre = "/AppOfflineMode/ghaziabad"
            openFragment(centre)
        }

    }

    private fun openFragment(centre: String) {
        val fragment = offlineModeCentre()
        val args = Bundle()
        args.putString("centre",centre)
        fragment.arguments = args

        val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun intializeviews() {

        val imagemap = frontPageMap!!["sliderImage"]  as Map<String, Any>
        val slideimages = imagemap.keys.toTypedArray()

        //Automatic Slider
        sliderView = binding.offlineModeSlider
        sliderAdapter = SliderAdapter(slideimages) //send the array of image
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        val imagemap2 = frontPageMap!!["2sliderImage"]  as Map<String, Any>
        val slideimages2 = imagemap2.keys.toTypedArray()

        sliderView = binding.offlineModeSlider2
        sliderAdapter = SliderAdapter(slideimages2) //send the array of image
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()


        val youtubelink = frontPageMap!!["exploreTheCentreVideoLink"].toString()
        lifecycle.addObserver(binding.youtubePlayerView2)

        binding.youtubePlayerView2.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(youtubelink, 0f)
            }
        })

        val youtubelink2 = frontPageMap!!["studentExperinceVideoLink"].toString()
        lifecycle.addObserver(binding.youtubePlayerView3)

        binding.youtubePlayerView3.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(youtubelink2, 0f)
            }
        })


    }

}