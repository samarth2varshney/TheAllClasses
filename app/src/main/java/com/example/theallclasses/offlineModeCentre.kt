package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.FragmentOfflineModeCentreBinding
import com.smarteist.autoimageslider.SliderView

class offlineModeCentre : Fragment() {

    private lateinit var binding: FragmentOfflineModeCentreBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    var map: Map<String, Any>? = null
    var bookSeat:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            map = (it.getSerializable("map") as? MutableMap<String, Any>)!!
            bookSeat = it.getBoolean("bookSeat")
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

        if(map!!["sliderImage"] != null){
            //Automatic Slider
            val imagemap = map!!["sliderImage"]  as Map<String, Any>
            val slideimages = imagemap.keys.toTypedArray()

            sliderView = binding.offlineModeCentreSlider
            sliderAdapter = SliderAdapter(slideimages)
            sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            sliderView.setSliderAdapter(sliderAdapter)
            sliderView.scrollTimeInSec = 3
            sliderView.isAutoCycle = true
            sliderView.startAutoCycle()
        }else{
           binding.offlineModeCentreSlider.visibility = View.GONE
        }

        if(map!!["contactInfo"] != null) {
            //phone and address
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data =
                Uri.parse("tel:${(map!!["contactInfo"] as Map<String, String>)["phoneNo"]}")
            binding.customercare2.append("${(map!!["contactInfo"] as Map<String, String>)["phoneNo"]} \n")
            binding.customercare2.append((map!!["contactInfo"] as Map<String, String>)["address"])
            binding.customercare2.setOnClickListener {
                startActivity(dialIntent)
            }
        }

        var mapWithName = map!!.toMutableMap()
        mapWithName.remove("sliderImage")
        mapWithName.remove("contactInfo")

        binding.offlinecentrecourcesrecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ExploreAndBookseatAdapter(requireContext(), mapWithName,bookSeat)
        binding.offlinecentrecourcesrecyclerview.adapter = adapter
    }

}