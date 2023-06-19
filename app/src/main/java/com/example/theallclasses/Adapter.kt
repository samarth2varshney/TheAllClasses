package com.example.theallclasses

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.Serializable

class Adapter(private val context: Context, private val mapData: Map<String, Any>, private val mapWithName: Map<String, Any>) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val keyTextView = itemView.findViewById<TextView>(R.id.chaptername)!!
        val imageView = itemView.findViewById<ImageView>(R.id.chapterimage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chapter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val map: Map<String, Any> = mapWithName[mapWithName.keys.elementAt(position)] as Map<String, Any>
        val map2: Map<String, Any> = mapData[mapData.keys.elementAt(position)] as Map<String, Any>
        val chaptername = map["name"].toString()
        val chapterimage = map["image"].toString()

        holder.keyTextView.text = chaptername
        Glide.with(holder.itemView).load(chapterimage).fitCenter().into(holder.imageView)

        holder.itemView.setOnClickListener{
            val mintent = Intent(context, Topic::class.java)
            mintent.putExtra("map", map2 as Serializable)
            context.startActivity(mintent)
        }

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}