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
import com.example.theallclasses.CourseDetails
import com.example.theallclasses.R
import com.example.theallclasses.offlineCourseDetails
import com.google.firebase.auth.FirebaseAuth
import java.io.Serializable

val auth = FirebaseAuth.getInstance()

class ExploreAndBookseatAdapter(
    private val context: Context,
    private val mapData: Map<String, Any>,
    val bookSeat: Boolean,
    val location: String,
    val type: String
) : RecyclerView.Adapter<ExploreAndBookseatAdapter.ViewHolder>(){

    class ViewHolder(itemView: View, activityview: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.pic2)
        val explorebutton = itemView.findViewById<Button>(R.id.exploreId)
        val layout = activityview.findViewById<FrameLayout>(R.id.frame_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.explore_and_buy_item, parent, false)
        val activityview = LayoutInflater.from(parent.context).inflate(R.layout.activity_main2, parent, false)
        return ViewHolder(view,activityview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(mapData[mapData.keys.elementAt(position)] is Map<*, *>) {
            val map: Map<String, Any> =
                mapData[mapData.keys.elementAt(position)] as Map<String, Any>

            if (type == "offline") {
                holder.explorebutton.text = "Explore the Center"
            }

            val courseimage = map["courseImage"].toString()

            Glide.with(holder.itemView).load(courseimage).fitCenter().into(holder.imageView)

            holder.explorebutton.setOnClickListener {
                if (type == "online") {
                    val fragment = CourseDetails()
                    val args = Bundle()
                    args.putSerializable("map", map as Serializable)
                    args.putString("location", location)
                    args.putString("type", type)
                    args.putString("startDate", map["startDate"].toString())
                    args.putString("endDate", map["endDate"].toString())
                    fragment.arguments = args
                    val fragmentManager: FragmentManager =
                        (context as AppCompatActivity).supportFragmentManager
                    val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                    transaction.replace(holder.layout.id, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                } else if (type == "offline") {
                    val fragment = offlineCourseDetails()
                    val args = Bundle()
                    args.putSerializable("map", map as Serializable)
                    args.putString("location", location)
                    args.putString("type", type)
                    args.putString("startDate", map["startDate"].toString())
                    args.putString("endDate", map["endDate"].toString())
                    fragment.arguments = args
                    val fragmentManager: FragmentManager =
                        (context as AppCompatActivity).supportFragmentManager
                    val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                    transaction.replace(holder.layout.id, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}