package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.FragmentOfflineModeCentreBinding
import com.smarteist.autoimageslider.SliderView

class offlineModeCentre : Fragment() {

    private lateinit var binding: FragmentOfflineModeCentreBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    var frontPageMap: Map<String, Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            frontPageMap = (it.getSerializable("map") as? MutableMap<String, Any>)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfflineModeCentreBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imagemap = frontPageMap!!["sliderImage"]  as Map<String, Any>
        val slideimages = imagemap.keys.toTypedArray()

        //Automatic Slider
        sliderView = binding.offlineModeCentreSlider
        sliderAdapter = SliderAdapter(slideimages) //send the array of image
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        var mapWithName = frontPageMap!!.toMutableMap()
        mapWithName.remove("sliderImage")

        binding.offlinecentrecourcesrecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ExploreAndBookseatAdapter(requireContext(),mapWithName)
        binding.offlinecentrecourcesrecyclerview.adapter = adapter
    }

}