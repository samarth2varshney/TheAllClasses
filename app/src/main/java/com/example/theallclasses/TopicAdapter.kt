package com.example.theallclasses

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.Serializable

class TopicAdapter(private val context: Context, private val mapData: Map<String, Any>, private val mapWithName: Map<String, Any>) : RecyclerView.Adapter<TopicAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val keyTextView = itemView.findViewById<TextView>(R.id.topicname)
        val imageView = itemView.findViewById<ImageView>(R.id.topicimage)
        val videobutton = itemView.findViewById<Button>(R.id.videobutton)
        val pdfbutton = itemView.findViewById<Button>(R.id.PDFbutton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var map: Map<String, Any> = mapWithName[mapWithName.keys.elementAt(position)] as Map<String, Any>

        val pdflink = map["pdf"].toString()
        val youtubelink = map["youtube"].toString()
        val imagelink = map["image"].toString()
        val topicname = map["name"].toString()

        holder.keyTextView.text = topicname
        Glide.with(holder.itemView).load(imagelink).fitCenter().into(holder.imageView)

        holder.videobutton.setOnClickListener{
            val intent2 = Intent(context,CustomUiActivity::class.java)
            intent2.putExtra("youtubelink",youtubelink)
            context.startActivity(intent2)
        }

        holder.pdfbutton.setOnClickListener {
            val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(pdflink))
            context.startActivity(intent3)
        }

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}