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
        val keyTextView = itemView.findViewById<TextView>(R.id.keyTextView1)!!
        val imageView = itemView.findViewById<ImageView>(R.id.pic1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        var map: Map<String, Any>
        var map2: Map<String, Any>
        if(mapWithName[mapWithName.keys.elementAt(position)] is Map<*, *> ) {
            map = mapWithName[mapWithName.keys.elementAt(position)] as Map<String, Any>
            holder.keyTextView.text = map["name"].toString()
            Glide.with(holder.itemView).load(map["image"]).fitCenter().into(holder.imageView)
        }
        val mintent = Intent(context, Recycler1::class.java)
        holder.itemView.setOnClickListener{
            if (mapData[mapData.keys.elementAt(position)] is Map<*, *>){
                map2 = mapData[mapData.keys.elementAt(position)] as Map<String, Any>
                if (mapData.keys.elementAt(position)[0]!='t'){
                    mintent.putExtra("map", map2 as Serializable)
                    context.startActivity(mintent)
                }
                else{
                    val intent2 = Intent(context,CustomUiActivity::class.java)
                    intent2.putExtra("youtubelink",map2["youtube"].toString())
                    context.startActivity(intent2)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}