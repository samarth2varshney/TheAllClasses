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

        SharedData.Mycourses = SharedData.CourseFlag.filterValues { it == true }
            .mapNotNull { (key, _) -> SharedData.JEEmap?.get(key)?.let { key to it } }
            .toMap()


        val OnlineCourses : Map<String,Map<String, Any>?> = mapOf("Boards" to SharedData.Boardmap,"JEE" to SharedData.JEEmap
            ,"NEET" to SharedData.NEETmap,"TeacherTraning" to SharedData.TeacherTraningCoursemap)

        binding.onlinecourserecyclerview.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        val horizontaladapter = HorizontalRecyclerAdapter(requireContext(),OnlineCourses)
        binding.onlinecourserecyclerview.adapter = horizontaladapter

        //Automatic Slider
        sliderView = binding.imageSlider

        sliderAdapter = SliderAdapter(SharedData.imagename)

        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        sliderView.setSliderAdapter(sliderAdapter)

        sliderView.scrollTimeInSec = 3

        sliderView.isAutoCycle = true

        sliderView.startAutoCycle()

    }
}