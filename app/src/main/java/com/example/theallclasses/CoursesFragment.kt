package com.example.theallclasses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        val MycoursesKeys = SharedData.Mycourses?.keys
        MycoursesKeys?.forEach { key ->
            val value = SharedData.JEEmap!![key]
            if (value != null) {
                SharedData.Mycoursesdata?.put(key, value)
            }
        }

        showcourses()
    }

    private fun showcourses() {
        val map = SharedData.Mycoursesdata

        val mapWithName = map!!.toMutableMap()

        binding.mycourserecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ExploreAdapter(requireContext(), mapWithName,false)
        binding.mycourserecyclerview.adapter = adapter
    }

}