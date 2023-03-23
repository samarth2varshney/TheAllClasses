package com.example.theallclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.databinding.ActivityRecycler1Binding
import java.io.Serializable

class Recycler1 : AppCompatActivity() {
    private lateinit var binding: ActivityRecycler1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecycler1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = intent

        val map = intent.getSerializableExtra("map") as? Map<String, Any>
        //var name = intent.getStringExtra("name")
        var map2: Map<String, Any>

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = Adapter(map!!)
        binding.recyclerView.adapter = adapter

        val mintent = Intent(this, Recycler2::class.java)
        adapter.setOnItemClickListener(object :Adapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                map2 = map[map.keys.elementAt(position)] as Map<String, Any>
                if (map[map.keys.elementAt(position)] is Map<*, *>){

                    if(map2[map2.keys.elementAt(0)] is Map<*,*>){
                        mintent.putExtra("map", map2 as Serializable)
                        startActivity(mintent)
                    }
                }
            }
        })

    }
}