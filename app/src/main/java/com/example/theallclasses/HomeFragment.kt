package com.example.theallclasses

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.ActivityMain2Binding
import com.example.theallclasses.databinding.FragmentHomeBinding
import com.smarteist.autoimageslider.SliderView
import java.io.Serializable

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var binding2: ActivityMain2Binding
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
        binding2 = ActivityMain2Binding.inflate(layoutInflater)

        val OnlineCourses : Map<String,Map<String, Any>?> = mapOf("Boards" to SharedData.Boardmap,"JEE" to SharedData.JEEmap
            ,"NEET" to SharedData.NEETmap,"TeacherTraning" to SharedData.TeacherTraningCoursemap)

        binding.onlinecourserecyclerview.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)
        val horizontaladapter = HorizontalRecyclerAdapter(requireContext(),OnlineCourses)
        binding.onlinecourserecyclerview.adapter = horizontaladapter

        binding.offlineButton.setOnClickListener {
//            startActivity(Intent(requireContext(),OfflineMode::class.java))
            val fragment = offlinemode1()
            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(binding2.frameLayout.id, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

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