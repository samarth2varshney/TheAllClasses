package com.example.theallclasses

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theallclasses.databinding.FragmentHomeBinding
import com.smarteist.autoimageslider.SliderView
//import com.smarteist.autoimageslider.SliderView
import java.io.Serializable

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.Text1.text = "Online classes"
        binding.Text2.text = "Offline classes"
        binding.Text3.text = "Home Tuttion"

        SharedData.Mycourses = SharedData.CourseFlag.filterValues { it == true }
            .mapNotNull { (key, _) -> SharedData.JEEmap?.get(key)?.let { key to it } }
            .toMap()
        //SharedData.Mycourses = SharedData.JEEmap

        binding.constraintLayout2.setOnClickListener {
            val intent = Intent(requireContext(), AllCourses::class.java)
            startActivity(intent)
        }

        //Automatic Slider
        sliderView = binding.imageSlider

        sliderAdapter = SliderAdapter(SharedData.imagename)

        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        sliderView.setSliderAdapter(sliderAdapter)

        sliderView.scrollTimeInSec = 3

        sliderView.isAutoCycle = true

        sliderView.startAutoCycle()

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/theallclasses.appspot.com/o/images%2Fdear.jpg?alt=media&token=664dada1-c761-4500-a30d-49a60484c193").fitCenter().into(binding.onlineimage)
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/theallclasses.appspot.com/o/images%2Fdear.jpg?alt=media&token=664dada1-c761-4500-a30d-49a60484c193").fitCenter().into(binding.offlineimage)
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/theallclasses.appspot.com/o/images%2Fdear.jpg?alt=media&token=664dada1-c761-4500-a30d-49a60484c193").fitCenter().into(binding.hometutionimage)

    }
}