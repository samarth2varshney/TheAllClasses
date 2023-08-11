package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.theallclasses.Adapters.Show_live_Adapter
import com.example.theallclasses.Adapters.testAdapter
import com.example.theallclasses.databinding.FragmentShowTestBinding

class showTest : Fragment() {

    lateinit var binding: FragmentShowTestBinding
    lateinit var map: Map<String, Any>
    var name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            map = (it.getSerializable("map") as? MutableMap<String, Any>)!!
            name = it.getString("name").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowTestBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this).load(map["banner"].toString()).fitCenter().into(binding.imageView9)

        if(map!!["tests"]!=null) {
            binding.recyclerView2.layoutManager =
                LinearLayoutManager(requireContext())
            val adapter = testAdapter(requireContext(),
                map!!["tests"] as Map<String, Any>)
            binding.recyclerView2.adapter = adapter
        }

        binding.textView33.text = name

    }

}