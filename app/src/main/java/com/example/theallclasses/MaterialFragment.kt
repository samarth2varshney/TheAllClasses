package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.theallclasses.databinding.FragmentMaterialBinding
import com.smarteist.autoimageslider.SliderView

class MaterialFragment : Fragment() {

    private lateinit var binding: FragmentMaterialBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(SharedData.MaterialFragmentData!!["booklet"].toString())
                )
            )
        }
        binding.btnCard2.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(SharedData.MaterialFragmentData!!["testseries"].toString())
                )
            )
        }
        binding.btnCard3.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(SharedData.MaterialFragmentData!!["tshirt"].toString())
                )
            )
        }
        binding.btnCard4.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(SharedData.MaterialFragmentData!!["accessories"].toString())
                )
            )
        }
    }
}