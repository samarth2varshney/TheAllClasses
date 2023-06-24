package com.example.theallclasses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.theallclasses.databinding.FragmentOfflinemode1Binding

class offlinemode1 : Fragment() {
    private lateinit var binding: FragmentOfflinemode1Binding
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfflinemode1Binding.inflate(layoutInflater)
        return (binding.root)
    }
}