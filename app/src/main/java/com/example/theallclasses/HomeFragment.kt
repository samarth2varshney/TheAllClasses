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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
            val Board = db.document("/customerCareNumber/numbers")
            Board.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.customerCare = document.data as Map<String, Any>
                    }
                }
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
        args.putString("location",s)
        args.putString("type","online")
        fragment.arguments = args

        val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}