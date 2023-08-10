package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.Adapters.LiveVideoAdapter
import com.example.theallclasses.Adapters.SliderAdapter
import com.example.theallclasses.databinding.FragmentLiveBinding
import com.smarteist.autoimageslider.SliderView

class LiveFragment : Fragment() {

    private lateinit var binding: FragmentLiveBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLiveBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Automatic Slider
        val imageurl = SharedData.LiveFragmentData!!["slider"] as Map<String, Any>
        sliderView = binding.imageSliderLive
        sliderAdapter = SliderAdapter(imageurl!!.keys.toTypedArray())
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

        if(SharedData.LiveFragmentData!!["liveVideos"]!=null) {
            binding.liverecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val adapter = LiveVideoAdapter(requireContext(),
                SharedData.LiveFragmentData!!["liveVideos"] as Map<String, Any>,false)
            binding.liverecyclerView.adapter = adapter
        }

        if(SharedData.LiveFragmentData!!["zoomMeetings"]!=null) {
            binding.zoomMeetingrecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val adapter = LiveVideoAdapter(requireContext(),
                SharedData.LiveFragmentData!!["zoomMeetings"] as Map<String, Any>,true)
            binding.zoomMeetingrecyclerview.adapter = adapter
        }

    }
}