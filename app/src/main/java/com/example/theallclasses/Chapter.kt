package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.FragmentChapterBinding

class Chapter : Fragment() {
    private lateinit var binding: FragmentChapterBinding
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
        binding = FragmentChapterBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapWithName = map.toMutableMap()
        val mapout = map.toMutableMap()
        mapout.remove("name")
        mapout.remove("image")
        mapout.remove("cost")
        mapout.remove("discount")
        mapout.remove("time")
        mapout.remove("originalCost")

        mapWithName.remove("name")
        mapWithName.remove("image")
        mapWithName.remove("cost")
        mapWithName.remove("discount")
        mapWithName.remove("time")
        mapWithName.remove("originalCost")

        binding.chapterrecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = Adapter(requireContext(),mapout,mapWithName!!)
        binding.chapterrecyclerview.adapter = adapter

    }

}