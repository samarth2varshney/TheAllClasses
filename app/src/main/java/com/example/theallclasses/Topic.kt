package com.example.theallclasses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.ActivityTopicBinding

class Topic : AppCompatActivity() {
    private lateinit var binding: ActivityTopicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent = intent

        val map = intent.getSerializableExtra("map") as? MutableMap<String, Any>

        val mapWithName = map
        map!!.remove("name")
        map.remove("image")
        map.remove("cost")
        map.remove("discount")
        map.remove("time")
        map.remove("originalCost")

        binding.recyclerviewtopic.layoutManager = LinearLayoutManager(this)
        val adapter = TopicAdapter(this,map!!,mapWithName!!)
        binding.recyclerviewtopic.adapter = adapter
    }
}