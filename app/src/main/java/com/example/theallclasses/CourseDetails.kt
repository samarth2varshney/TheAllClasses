package com.example.theallclasses

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.theallclasses.databinding.FragmentCourseDetailsBinding

class CourseDetails : Fragment() {

    private lateinit var binding: FragmentCourseDetailsBinding
    lateinit var map:MutableMap<String,Any>
    var location:String = ""
    var type:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            map = (it.getSerializable("map") as? MutableMap<String, Any>)!!
            location = it.getString("location").toString()
            type = it.getString("type").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCourseDetailsBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(map["courseDetails"] != null)
            Glide.with(this).load(map["courseDetails"]).fitCenter().into(binding.courseDetailsImage)

        if(map["otherDetails"] != null)
            Glide.with(this).load(map["otherDetails"]).fitCenter().into(binding.otherDetailsImage)
        else{
            binding.otherDetailsImage.visibility = View.GONE
            binding.otherDetails.visibility = View.GONE
        }

        binding.bookSeatbutton.setOnClickListener {
            val intent = Intent(requireContext(), PurchaseActivity::class.java)
            if(map["cost"]!="null"){
                intent.putExtra("courseName" ,map["name"].toString())
                intent.putExtra("cost", map["cost"].toString().toInt())
                intent.putExtra("location",location)
                intent.putExtra("type",type)
                intent.putExtra("startDate",map["startDate"].toString())
                intent.putExtra("endDate",map["endDate"].toString())
                startActivity(intent)
            }
        }

        if(map["freeContent"] != null) {
            val mapWithName = map["freeContent"] as MutableMap<String, Any>

            binding.freecourserecyclerview.layoutManager = LinearLayoutManager(requireContext())
            val adapter = Adapter(requireContext(), mapWithName)
            binding.freecourserecyclerview.adapter = adapter
        }else{
            binding.freecourserecyclerview.visibility = View.GONE
            binding.textView6.visibility = View.GONE
        }
    }

}