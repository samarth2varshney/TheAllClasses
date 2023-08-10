package com.example.theallclasses.Adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theallclasses.R
import com.example.theallclasses.Show_lives
import java.io.Serializable

class LiveVideoAdapter(
    private val context: Context,
    private val mapData: Map<String, Any>,
    private val show_youtubetext: Boolean
) : RecyclerView.Adapter<LiveVideoAdapter.ViewHolder>(){

    class ViewHolder(itemView: View, activityview: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.liveVideo)
        val button = itemView.findViewById<Button>(R.id.button5)
        val layout = activityview.findViewById<FrameLayout>(R.id.frame_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.live_video_item, parent, false)
        val activityview = LayoutInflater.from(parent.context).inflate(R.layout.activity_main2, parent, false)
        return ViewHolder(view,activityview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val map = mapData[mapData.keys.elementAt(position)] as Map<String,Any>

        Glide.with(holder.itemView).load(map["image"].toString()).fitCenter().into(holder.imageView)

        if(show_youtubetext){
            holder.button.text = "Click For Zoom Meetings"
        }

        holder.button.setOnClickListener {
            val fragment = Show_lives()
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