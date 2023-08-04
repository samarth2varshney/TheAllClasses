package com.example.theallclasses
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.FragmentCoursesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class CoursesFragment : Fragment() {

    private lateinit var binding: FragmentCoursesBinding
    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore
    var flag1=false
    var flag2=false
    var flag3=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesBinding.inflate(layoutInflater)
        return (binding.root)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textView16.visibility = View.GONE

        if(auth.currentUser!=null) {
            SharedData.uid = auth.currentUser!!.uid
            val courses = db.document("/users/${SharedData.uid}")
            courses.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.Mycourses = document.data!!["mycourses"] as? HashMap<String, HashMap<String,String>>?
                        flag1 = true
                        checkifloaded()
                    }
                }
        }

        if(SharedData.OfflineModeData==null) {
            val Board = db.document("/Offline/Offline")
            Board.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.OfflineModeData = document.data as MutableMap<String, Any>
                        flag2 = true
                        checkifloaded()
                    }
                }
        }else{
            flag2 = true
            checkifloaded()
        }

        if(SharedData.HomeTuitionData == null) {
            val HomeTution = db.document("/Home_Tuition/Home_Tuition")
            HomeTution.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.HomeTuitionData = document.data as Map<String, Any>
                        SharedData.teacherTrainingProgram = SharedData.HomeTuitionData!!["teacherTrainingProgram"] as Map<String,Any>
                        flag3 = true
                        checkifloaded()
                    }
                }
        }else{
            flag3 = true
            checkifloaded()
        }

    }

    override fun onResume() {
        super.onResume()

        if(auth.currentUser!=null) {
            SharedData.uid = auth.currentUser!!.uid
            val courses = db.document("/users/${SharedData.uid}")
            courses.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.Mycourses = document.data!!["mycourses"] as? HashMap<String, HashMap<String,String>>?
                        flag1 = true
                        flag2 = true
                        flag3 = true
                        checkifloaded()
                    }
                }
        }


    }

    fun showcourses() {
        binding.spinner.visibility = View.GONE

        val map = SharedData.Mycoursesdata

        val mapWithName = map!!.toMutableMap()

        binding.mycourserecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val adapter = BuyedCourseAdapter(requireContext(), mapWithName)
        binding.mycourserecyclerview.adapter = adapter
    }

    fun copyCourses(){
        val MycoursesKeys = SharedData.Mycourses?.keys
        val currentDate = LocalDate.now()
        val dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy")

        MycoursesKeys?.forEach { key ->

            val map = SharedData.Mycourses!![key] as HashMap<String, Any>

             if(map["location"].toString() == "jeemains"){
                 val value = SharedData.JEEmap!![map["courseName"]]
                 val dateFromInput = LocalDate.parse(map["endDate"].toString(), dateFormat)
                 val comparisonResult = currentDate.compareTo(dateFromInput)

                 if (value != null && comparisonResult<0) {
                    SharedData.Mycoursesdata?.put(key, value)
                }
            }
            else if(map["location"].toString() == "jeeadvance"){
                val value = SharedData.JEE_Advanced!![map["courseName"]]
                 val dateFromInput = LocalDate.parse(map["endDate"].toString(), dateFormat)
                 val comparisonResult = currentDate.compareTo(dateFromInput)

                if (value != null && comparisonResult<0) {
                    SharedData.Mycoursesdata?.put(key, value)
                }
            }
            else if(map["location"].toString() == "neet"){
                val value = SharedData.NEETmap!![map["courseName"]]
                 val dateFromInput = LocalDate.parse(map["endDate"].toString(), dateFormat)
                 val comparisonResult = currentDate.compareTo(dateFromInput)

                if (value != null && comparisonResult<0) {
                    SharedData.Mycoursesdata?.put(key, value)
                }
            }
            else if(map["location"].toString() == "cbse"){
                val value = SharedData.Boardmap!![map["courseName"]]
                 val dateFromInput = LocalDate.parse(map["endDate"].toString(), dateFormat)
                 val comparisonResult = currentDate.compareTo(dateFromInput)

                if (value != null && comparisonResult<0) {
                    SharedData.Mycoursesdata?.put(key, value)
                }
            }
            else if(map["location"].toString() == "teachertraining"){ //teachertraining
                val value = SharedData.teacherTrainingProgram!![map["courseName"]]
                 val dateFromInput = LocalDate.parse(map["endDate"].toString(), dateFormat)
                 val comparisonResult = currentDate.compareTo(dateFromInput)

                if (value != null && comparisonResult<0) {
                    SharedData.Mycoursesdata?.put(key, value)
                }
            }
            else if(map["location"].toString() == "newDelhi"){
                val newDelhimap = SharedData.OfflineModeData!!["newDelhi"] as Map<String,Any>
                val value = newDelhimap[map["courseName"]]
                 val dateFromInput = LocalDate.parse(map["endDate"].toString(), dateFormat)
                 val comparisonResult = currentDate.compareTo(dateFromInput)

                if (value != null && comparisonResult<0) {
                    SharedData.Mycoursesdata?.put(key, value)
                }
            }
             else if(map["location"].toString() == "noida"){
                 val newDelhimap = SharedData.OfflineModeData!!["noida"] as Map<String,Any>
                 val value = newDelhimap[map["courseName"]]
                 val dateFromInput = LocalDate.parse(map["endDate"].toString(), dateFormat)
                 val comparisonResult = currentDate.compareTo(dateFromInput)

                 if (value != null && comparisonResult<0) {
                     SharedData.Mycoursesdata?.put(key, value)
                 }
             }
             else if(map["location"].toString() == "ghaziabad"){
                 val newDelhimap = SharedData.OfflineModeData!!["ghaziabad"] as Map<String,Any>
                 val value = newDelhimap[map["courseName"]]
                 val dateFromInput = LocalDate.parse(map["endDate"].toString(), dateFormat)
                 val comparisonResult = currentDate.compareTo(dateFromInput)

                 if (value != null && comparisonResult<0) {
                     SharedData.Mycoursesdata?.put(key, value)
                 }
             }

        }

        showcourses()
    }

    private fun checkifloaded() {

        if(flag1 && flag2 && flag3){

            if(SharedData.Mycourses!!.isEmpty()) {
                binding.spinner.visibility = View.GONE
                binding.textView16.visibility = View.VISIBLE
            }
            else{
                copyCourses()
            }

        }
    }

}