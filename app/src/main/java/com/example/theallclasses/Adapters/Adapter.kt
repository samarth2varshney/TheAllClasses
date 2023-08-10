package com.example.theallclasses.Adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theallclasses.R
import com.example.theallclasses.topic1
import java.io.Serializable

class Adapter(private val context: Context, private val mapData: Map<String, Any>) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    class ViewHolder(itemView: View, activityview: View) : RecyclerView.ViewHolder(itemView) {
        val keyTextView = itemView.findViewById<TextView>(R.id.chaptername)!!
        val imageView = itemView.findViewById<ImageView>(R.id.chapterimage)
        val layout = activityview.findViewById<FrameLayout>(R.id.frame_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chapter_item, parent, false)
        val activityview = LayoutInflater.from(parent.context).inflate(R.layout.activity_main2, parent, false)
        return ViewHolder(view,activityview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val map: Map<String, Any> = mapData[mapData.keys.elementAt(position)] as Map<String, Any>
        val chaptername = map["chapterName"].toString()
        val chapterimage = map["chapterImage"].toString()

        holder.keyTextView.text = chaptername
        Glide.with(holder.itemView).load(chapterimage).fitCenter().into(holder.imageView)

        holder.itemView.setOnClickListener{

            val fragment = topic1()
            val args = Bundle()
            args.putSerializable("map", map as Serializable)
            fragment.arguments = args

            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(holder.layout.id, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}