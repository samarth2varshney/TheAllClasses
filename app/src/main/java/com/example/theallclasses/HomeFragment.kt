package com.example.theallclasses

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val mintent = Intent(activity, Recycler1::class.java)

        binding.Boardsbtn.setOnClickListener {
            mintent.putExtra("map", SharedData.Boardmap as Serializable)
            startActivity(mintent)
        }
        binding.JEEbtn.setOnClickListener {
            mintent.putExtra("map", SharedData.JEEmap as Serializable)
            startActivity(mintent)
        }

        binding.NEETbtn.setOnClickListener {
            mintent.putExtra("map", SharedData.NEETmap as Serializable)
            startActivity(mintent)
        }

        binding.TeacherTraningbtn.setOnClickListener {
            mintent.putExtra("map", SharedData.TeacherTraningCoursemap as Serializable)
            startActivity(mintent)
        }

//        binding.purchaseBtn.setOnClickListener {
//            startActivity(Intent(activity,PurchaseActivity::class.java))
//        }
        sliderView = binding.imageSlider

        sliderAdapter = SliderAdapter(SharedData.imagename)

        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR

        sliderView.setSliderAdapter(sliderAdapter)

        sliderView.scrollTimeInSec = 3

        sliderView.isAutoCycle = true

        sliderView.startAutoCycle()

    }
}