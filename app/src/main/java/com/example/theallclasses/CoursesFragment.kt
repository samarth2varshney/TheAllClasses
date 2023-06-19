package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.FragmentCoursesBinding

class CoursesFragment : Fragment() {

    private lateinit var binding: FragmentCoursesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesBinding.inflate(layoutInflater)

        return (binding.root)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val map = SharedData.Mycourses as? MutableMap<String, Any>

        val mapWithName = map
        map!!.remove("name")
        map.remove("image")
        map.remove("cost")
        map.remove("discount")
        map.remove("time")
        map.remove("originalCost")

        binding.mycourserecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ExploreAndBuyAdapter(requireContext(),map!!,mapWithName!!)
        binding.mycourserecyclerview.adapter = adapter

    }

}