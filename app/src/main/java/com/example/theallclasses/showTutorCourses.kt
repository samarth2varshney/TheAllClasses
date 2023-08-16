package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.theallclasses.Adapters.ExploreAndBookseatAdapter
import com.example.theallclasses.Adapters.SliderAdapter
import com.example.theallclasses.databinding.FragmentShowCoursesBinding
import com.smarteist.autoimageslider.SliderView
import java.io.Serializable

class showTutorCourses : Fragment() {

    private lateinit var binding: FragmentShowCoursesBinding
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
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
        binding = FragmentShowCoursesBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(SharedData.HomeTuitionData!!["teacherTraningOfflineImage"]).fitCenter().into(binding.imageView11)
        Glide.with(this).load(SharedData.HomeTuitionData!!["teacherTraningOnlineImage"]).fitCenter().into(binding.imageView12)

        if(SharedData.HomeTuitionData!!["joinTeacherTranningProgramForm"]!=null){
            binding.button4.setOnClickListener {
                val fragment = WebviewFragment()
                val args = Bundle()
                args.putString("formlink",SharedData.HomeTuitionData!!["joinTeacherTranningProgramForm"].toString())
                fragment.arguments = args

                val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                transaction.replace(containerId, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        binding.button15.setOnClickListener {
            if(SharedData.HomeTuitionData!!["teacherTrainingProgram"]!=null) {
                val tutormap = SharedData.HomeTuitionData!!["teacherTrainingProgram"] as Map<String, Any>
                val fragment = showOnlineCourses()
                val args = Bundle()
                args.putSerializable("map", tutormap as Serializable)
                args.putBoolean("bookSeat", false)
                args.putString("location", location)
                args.putString("type", "online")
                fragment.arguments = args
                val fragmentManager: FragmentManager =
                    (context as AppCompatActivity).supportFragmentManager
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                transaction.replace(containerId, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        binding.button17.setOnClickListener {
            if(SharedData.HomeTuitionData!!["teacherTrainingProgramoffline"]!=null) {
                val tutormap = SharedData.HomeTuitionData!!["teacherTrainingProgramoffline"] as Map<String, Any>
                val fragment = showOfflineCourses()
                val args = Bundle()
                args.putSerializable("map", tutormap as Serializable)
                args.putBoolean("bookSeat", true)
                args.putString("location", "teachertraning")
                args.putString("type", "offline")
                fragment.arguments = args

                val fragmentManager: FragmentManager =
                    (context as AppCompatActivity).supportFragmentManager
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                transaction.replace(containerId, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        if(SharedData.HomeTuitionData!!["joinAsTeacherSlider"] != null){
            val imagemap = SharedData.HomeTuitionData!!["joinAsTeacherSlider"]  as Map<String, Any>
            val slideimages = imagemap.keys.toTypedArray()

            sliderView = binding.offlineModeCentreSlider
            sliderAdapter = SliderAdapter(slideimages)
            sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            sliderView.setSliderAdapter(sliderAdapter)
            sliderView.scrollTimeInSec = 3
            sliderView.isAutoCycle = true
            sliderView.startAutoCycle()
        }

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

}