package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.Adapters.ExploreAndBookseatAdapter
import com.example.theallclasses.Adapters.SliderAdapter
import com.example.theallclasses.databinding.FragmentShowOfflineCoursesBinding
import com.smarteist.autoimageslider.SliderView

class showOfflineCourses : Fragment() {

    lateinit var binding: FragmentShowOfflineCoursesBinding
    lateinit var map: Map<String, Any>
    var location:String = ""
    var type:String = ""
    var bookSeat:Boolean = false
    var containerId:Int = 0

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
        containerId = container?.id!!
        binding = FragmentShowOfflineCoursesBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mapWithName = map!!.toMutableMap()
        mapWithName.remove("slider")
        mapWithName.remove("contactInfo")

        if(mapWithName.isNotEmpty()) {
            binding.textView15.visibility = View.GONE
            binding.offlineCourcesrecyclerview.layoutManager =
                LinearLayoutManager(requireContext())
            val adapter = ExploreAndBookseatAdapter(requireContext(), mapWithName, bookSeat, location, type)
            binding.offlineCourcesrecyclerview.adapter = adapter
            binding.textView15.visibility = View.GONE
        }

        if(map!!["slider"] != null){
            val imagemap = map!!["slider"]  as Map<String, Any>
            intializeslider(imagemap,binding.offlineModeCentreSlider)
        }

        binding.textView17.append(" $location")

        contactInfo()

    }

    private fun contactInfo() {
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
    }

    private fun intializeslider(imagesMap: Map<String, Any>, ModeSlider: SliderView) {
        var sliderView: SliderView = ModeSlider
        var sliderAdapter = SliderAdapter(imagesMap.keys.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }



}