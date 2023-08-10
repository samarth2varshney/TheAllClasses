package com.example.theallclasses

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.theallclasses.Adapters.Show_live_Adapter
import com.example.theallclasses.databinding.FragmentShowLivesBinding

class Show_lives : Fragment() {

    lateinit var map: Map<String, Any>
    lateinit var binding: FragmentShowLivesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            map = (it.getSerializable("map") as? MutableMap<String, Any>)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowLivesBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button7.setOnClickListener {
            val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(map["whatsappPoll"].toString()))
            startActivity(intent3)
        }

        Glide.with(this).load(map["image"].toString()).fitCenter().into(binding.imageView3)

        if(map!!["videoLinks"]!=null) {
            binding.recyclerView.layoutManager =
                LinearLayoutManager(requireContext())
            val adapter = Show_live_Adapter(requireContext(),
                map!!["videoLinks"] as Map<String, Any>)
            binding.recyclerView.adapter = adapter
        }

    }

}