package com.example.theallclasses.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theallclasses.R

class MaterialRecyclerAdapter(private val context: Context, private val mapData: Map<String, Any>) : RecyclerView.Adapter<MaterialRecyclerAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView = itemView.findViewById<ImageView>(R.id.pic1)
        val button = itemView.findViewById<Button>(R.id.studybt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.buyed_course_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val map = mapData[mapData.keys.elementAt(position)] as Map<String, Any>

        Glide.with(holder.itemView).load(map["image"]).fitCenter().into(holder.imageView)

        holder.button.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map["link"].toString()))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}