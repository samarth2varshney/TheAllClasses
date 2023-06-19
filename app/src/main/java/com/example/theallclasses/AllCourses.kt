package com.example.theallclasses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theallclasses.databinding.ActivityAllCoursesBinding

class AllCourses : AppCompatActivity() {
    private lateinit var binding: ActivityAllCoursesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllCoursesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courses : Map<String,Map<String, Any>?> = mapOf("Boards" to SharedData.Boardmap,"JEE" to SharedData.JEEmap
            ,"NEET" to SharedData.NEETmap,"TeacherTraning" to SharedData.TeacherTraningCoursemap)

        binding.allcoursesrecyclerview.layoutManager = LinearLayoutManager(this)
        val horizontaladapter = HorizontalRecyclerAdapter(this,courses)
        binding.allcoursesrecyclerview.adapter = horizontaladapter

    }
}