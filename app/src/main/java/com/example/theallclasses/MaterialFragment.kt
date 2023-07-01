package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.theallclasses.databinding.FragmentMaterialBinding
import com.smarteist.autoimageslider.SliderView
import java.io.Serializable

class MaterialFragment : Fragment() {

    private lateinit var binding: FragmentMaterialBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    var containerId:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        containerId = container?.id!!
        binding = FragmentMaterialBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Automatic Slider
        val imageurl = SharedData.MaterialFragmentData!!["slider"] as Map<String, Any>
        sliderView = binding.imageSliderMaterial
        sliderAdapter = SliderAdapter(imageurl!!.keys.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        binding.btnCard1.setOnClickListener {
            openShowMaterial(SharedData.MaterialFragmentData!!["booklets"] as Map<String,Any>)
        }
        binding.btnCard2.setOnClickListener {
            openShowMaterial(SharedData.MaterialFragmentData!!["testSeries"] as Map<String,Any>)
        }
        binding.btnCard3.setOnClickListener {
            openShowMaterial(SharedData.MaterialFragmentData!!["tShirts"] as Map<String,Any>)
        }
        binding.btnCard4.setOnClickListener {
            openShowMaterial(SharedData.MaterialFragmentData!!["otherAccessories"] as Map<String,Any>)
        }
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