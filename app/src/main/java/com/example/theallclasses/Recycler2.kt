package com.example.theallclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.ActivityRecycler2Binding
import java.io.Serializable

class Recycler2 : AppCompatActivity() {
    private lateinit var binding: ActivityRecycler2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycler2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = intent
        val map = intent.getSerializableExtra("map") as? Map<String, Any>
        var map2: Map<String, Any>
        //var name = intent.getStringExtra("name")
        //supportActionBar?.title = name

        binding.recyclerView2.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(map!!)
        binding.recyclerView2.adapter = adapter

        val mintent = Intent(this, Recycler1::class.java)
        adapter.setOnItemClickListener(object : Adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                if (map[map.keys.elementAt(position)] is Map<*, *>){
                    map2 = map[map.keys.elementAt(position)] as Map<String, Any>
                    if(map2[map2.keys.elementAt(0)] is Map<*,*>){
                        mintent.putExtra("map", map2 as Serializable)
                        startActivity(mintent)
                    }else{

                    }
                }
            }
        })

    }
}