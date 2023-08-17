package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.theallclasses.databinding.FragmentCourseDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import java.io.Serializable

class CourseDetails : Fragment() {

    private lateinit var binding: FragmentCourseDetailsBinding
    lateinit var map:MutableMap<String,Any>
    var location:String = ""
    var type:String = ""
    var containerId = 0
    val auth = FirebaseAuth.getInstance()

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
        containerId = container?.id!!
        binding = FragmentCourseDetailsBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button12.setOnClickListener {
            val fragment = Tests()
            val args = Bundle()
            args.putSerializable("map", SharedData.MaterialFragmentData!!["testSeries"] as Map<String,Any> as Serializable)
            fragment.arguments = args
            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        if(map["courseDetails"] != null)
            Glide.with(this).load(map["courseDetails"]).fitCenter().into(binding.courseDetailsImage)

        Glide.with(this).load(map["teachersImage"]).fitCenter().into(binding.teacherImage)
        Glide.with(this).load(map["testImage"]).fitCenter().into(binding.testImage)
        Glide.with(this).load(map["studyMaterialImage"]).fitCenter().into(binding.studyMaterial)

        binding.button13.setOnClickListener {
            openShowMaterial(SharedData.MaterialFragmentData!!["booklets"] as Map<String,Any>)
        }

        if(map["otherDetails"] != null)
            Glide.with(this).load(map["otherDetails"]).fitCenter().into(binding.otherDetailsImage)
        else{
            binding.otherDetailsImage.visibility = View.GONE
            binding.otherDetails.visibility = View.GONE
        }

        binding.bookSeatbutton.setOnClickListener {
            if(auth.currentUser!=null) {
                val intent = Intent(requireContext(), PurchaseActivity::class.java)
                if(map["cost"]!="null"){
                    intent.putExtra("courseImage" ,map["courseDetails"].toString())
                    intent.putExtra("courseName" ,map["name"].toString())
                    intent.putExtra("cost", map["cost"].toString().toInt())
                    intent.putExtra("location",location)
                    intent.putExtra("type",type)
                    intent.putExtra("startDate",map["startDate"].toString())
                    intent.putExtra("endDate",map["endDate"].toString())
                    startActivity(intent)
                }
            }else{
                startActivity(Intent(requireContext(), SignInActivity::class.java))
            }
        }

        if(map["freeContent"] != null) {
            binding.button9.setOnClickListener {
                val fragment = ShowFreeContent()
                val args = Bundle()
                args.putString("location",location)
                args.putString("type",type)
                args.putSerializable("map", map as Serializable)
                fragment.arguments = args
                val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                transaction.replace(containerId, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }else{
            binding.button9.visibility = View.GONE
        }

        if(map!!["contactInfo"] != null) {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data =
                Uri.parse("tel:${(map!!["contactInfo"] as Map<String, String>)["phoneNo"]}")
            binding.customercare.append("${(map!!["contactInfo"] as Map<String, String>)["phoneNo"]} \n")
            binding.customercare.append((map!!["contactInfo"] as Map<String, String>)["address"])
            binding.button.setOnClickListener {
                startActivity(dialIntent)
            }
        }else if(SharedData.customerCare!=null){
            val dialIntent = Intent(Intent.ACTION_DIAL)

            dialIntent.data =
                Uri.parse("tel:${SharedData.customerCare!!["homeTuitionNumber"]}")
            binding.customercare.append("${SharedData.customerCare!!["homeTuitionNumber"]} \n")
            binding.button.setOnClickListener {
                startActivity(dialIntent)
            }
        }

    }

    private fun openShowMaterial(course: Map<String, Any>?) {
        val fragment = ShowMaterial()
        val args = Bundle()
        args.putSerializable("map", course as Serializable)
        fragment.arguments = args

        val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}