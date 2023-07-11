package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.FragmentShowCoursesBinding
import com.smarteist.autoimageslider.SliderView

class ShowCourses : Fragment() {

    private lateinit var binding: FragmentShowCoursesBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    lateinit var map: Map<String, Any>
    var location:String = ""
    var type:String = ""
    var bookSeat:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            map = (it.getSerializable("map") as? MutableMap<String, Any>)!!
            bookSeat = it.getBoolean("bookSeat")
            location = it.getString("location").toString()
            type = it.getString("type").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowCoursesBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(map!!["slider"] != null){
            //Automatic Slider
            val imagemap = map!!["slider"]  as Map<String, Any>
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
            //making the top margin to 0dp offlinecentrecourcesrecyclerview
            val layoutParams = binding.offlinecentrecourcesrecyclerview.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topMargin = 0
            binding.offlinecentrecourcesrecyclerview.layoutParams = layoutParams
        }

        if(map!!["contactInfo"] != null) {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data =
                Uri.parse("tel:${(map!!["contactInfo"] as Map<String, String>)["phoneNo"]}")
            binding.customercare2.append("${(map!!["contactInfo"] as Map<String, String>)["phoneNo"]} \n")
            binding.customercare2.append((map!!["contactInfo"] as Map<String, String>)["address"])
            binding.button2.setOnClickListener {
                startActivity(dialIntent)
            }
        }else if(SharedData.customerCare!=null){
            val dialIntent = Intent(Intent.ACTION_DIAL)

            dialIntent.data =
                Uri.parse("tel:${SharedData.customerCare!!["homeTuitionNumber"]}")
            binding.customercare2.append("${SharedData.customerCare!!["homeTuitionNumber"]} \n")
            binding.button2.setOnClickListener {
                startActivity(dialIntent)
            }
        }

        var mapWithName = map!!.toMutableMap()
        mapWithName.remove("slider")
        mapWithName.remove("contactInfo")

        if(mapWithName.isNotEmpty()) {
            binding.textView15.visibility = View.GONE
            binding.offlinecentrecourcesrecyclerview.layoutManager =
                LinearLayoutManager(requireContext())
            val adapter =
                ExploreAndBookseatAdapter(requireContext(), mapWithName, bookSeat, location, type)
            binding.offlinecentrecourcesrecyclerview.adapter = adapter
        }

    }

}