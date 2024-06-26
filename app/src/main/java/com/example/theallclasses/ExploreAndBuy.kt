package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.Adapters.BuyedCourseAdapter
import com.example.theallclasses.databinding.FragmentExploreAndBuyBinding

class ExploreAndBuy : Fragment() {

    private lateinit var binding: FragmentExploreAndBuyBinding
    lateinit var map:MutableMap<String,Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            map = (it.getSerializable("map") as? MutableMap<String, Any>)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreAndBuyBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapWithName = map.toMutableMap()
        mapWithName.remove("name")
        mapWithName.remove("image")

        binding.explorerecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = BuyedCourseAdapter(requireContext(), mapWithName)
        binding.explorerecyclerview.adapter = adapter

    }
}