package com.example.theallclasses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.theallclasses.databinding.FragmentHomeBinding
import com.smarteist.autoimageslider.SliderView
import java.io.Serializable
// sending loaction in home fragment

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    var containerId:Int = 0

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

        binding.jeeAdvancedButton.setOnClickListener{
            openShowCourses(SharedData.JEE_Advanced,"ADVANCED")
        }
        binding.jeeMainsButton.setOnClickListener {
            openShowCourses(SharedData.JEEmap, "JEE")
        }
        binding.neetUgButton.setOnClickListener {
            openShowCourses(SharedData.NEETmap, "NEET")
        }
        binding.cbseButton.setOnClickListener {
            openShowCourses(SharedData.Boardmap, "BOARDS")
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

        //Automatic Slider
        sliderView = binding.imageSlider
        sliderAdapter = SliderAdapter((SharedData.HomeFragmentData!!["homePageSlider1"] as Map<String, Any>).keys.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

    }

    private fun openShowCourses(course: Map<String, Any>?, s: String) {
        val fragment = ShowCourses()
        val args = Bundle()
        args.putSerializable("map", course as Serializable)
        args.putBoolean("bookSeat",false)
        args.putString("location","online")
        args.putString("type",s)
        fragment.arguments = args

        val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}