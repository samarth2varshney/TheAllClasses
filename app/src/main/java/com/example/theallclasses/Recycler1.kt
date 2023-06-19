package com.example.theallclasses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.ActivityRecycler1Binding

class Recycler1 : AppCompatActivity() {
    private lateinit var binding: ActivityRecycler1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycler1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val map = intent.getSerializableExtra("map") as? MutableMap<String, Any>

        val mapWithName = map
        map!!.remove("name")
        map.remove("image")
        map.remove("cost")
        map.remove("discount")
        map.remove("time")
        map.remove("originalCost")

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(this,map!!,mapWithName!!)
        binding.recyclerView.adapter = adapter

    }
}