package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.theallclasses.databinding.FragmentOfflineCourseDetailsBinding

class offlineCourseDetails : Fragment() {

    private lateinit var binding: FragmentOfflineCourseDetailsBinding
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

        binding = FragmentOfflineCourseDetailsBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(map["courseDetails"]).fitCenter().into(binding.courseDetailsImage)
        Glide.with(this).load(map["otherDetails"]).fitCenter().into(binding.otherDetailsImage)

    }

}