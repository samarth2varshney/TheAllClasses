package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theallclasses.Adapters.ChapterAdapter
import com.example.theallclasses.databinding.FragmentChapterBinding

class Chapter : Fragment() {
    private lateinit var binding: FragmentChapterBinding
    lateinit var map:MutableMap<String,Any>
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
        binding = FragmentChapterBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapWithName = map.toMutableMap().toSortedMap()

        binding.chapterrecyclerview.layoutManager = LinearLayoutManager(requireContext())
        val chapterAdapter = ChapterAdapter(requireContext(),mapWithName)
        binding.chapterrecyclerview.adapter = chapterAdapter

    }

}