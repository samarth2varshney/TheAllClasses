package com.example.theallclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.ActivityExploreAndBuyBinding
import java.io.Serializable

class ExploreAndBuy : AppCompatActivity() {
    private lateinit var binding: ActivityExploreAndBuyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreAndBuyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = intent

        val map = intent.getSerializableExtra("map") as? MutableMap<String, Any>

        val mapWithName = map
        map!!.remove("name")
        map!!.remove("image")

        binding.courserecyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = ExploreAndBuyAdapter(this,map!!,mapWithName!!)
        binding.courserecyclerview.adapter = adapter


    }
}