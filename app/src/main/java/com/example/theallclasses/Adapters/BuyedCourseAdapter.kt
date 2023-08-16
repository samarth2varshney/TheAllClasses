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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theallclasses.Chapter
import com.example.theallclasses.R
import java.io.Serializable

class BuyedCourseAdapter(
    private val context: Context,
    private val mapData: Map<String, Any>
) : RecyclerView.Adapter<BuyedCourseAdapter.ViewHolder>(){

    class ViewHolder(itemView: View, activityview: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.pic1)
        val explorebutton = itemView.findViewById<Button>(R.id.studybt)
        val layout = activityview.findViewById<FrameLayout>(R.id.frame_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.buyed_course_item, parent, false)
        val activityview = LayoutInflater.from(parent.context).inflate(R.layout.activity_main2, parent, false)
        return ViewHolder(view,activityview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val map: Map<String, Any> = mapData[mapData.keys.elementAt(position)] as Map<String, Any>

        holder.explorebutton.text = "Let's Study"

        val courseimage = map["courseImage"].toString()

        Glide.with(holder.itemView).load(courseimage).fitCenter().into(holder.imageView)

        if(map["paidContent"]!=null) {
            holder.explorebutton.setOnClickListener {
                val fragment = Chapter()
                val args = Bundle()
                args.putSerializable("map", map["paidContent"] as Serializable)
                fragment.arguments = args

                val fragmentManager: FragmentManager =
                    (context as AppCompatActivity).supportFragmentManager
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                transaction.replace(holder.layout.id, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        else{
            holder.explorebutton.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}