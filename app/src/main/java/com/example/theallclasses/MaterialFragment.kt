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
        sliderView = binding.imageSliderMaterial
        sliderAdapter = SliderAdapter(SharedData.imagename)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

//        binding.btn.setOnClickListener{
//            startActivity(
//                Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("http://www.youtube.com/watch?v=cxLG2wtE7TM")
//                )
//            )
//        }
    }
}