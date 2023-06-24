package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.FragmentTopic1Binding

class topic1 : Fragment() {
    private lateinit var binding: FragmentTopic1Binding
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
        binding = FragmentTopic1Binding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapWithName = map.toMutableMap()
        val mapWithoutName = map.toMutableMap()
        mapWithoutName.remove("name")
        mapWithoutName.remove("image")
        mapWithName.remove("name")
        mapWithName.remove("image")

        binding.topicrecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = TopicAdapter(requireContext(),mapWithoutName,mapWithName)
        binding.topicrecyclerview.adapter = adapter

    }

}