package com.example.theallclasses.Adapters

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
import com.example.theallclasses.CustomUiActivity
import com.example.theallclasses.R
import java.io.Serializable

class TopicAdapter(private val context: Context, private val mapData: Map<String, Any>) : RecyclerView.Adapter<TopicAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.topicimage)
        val pdfbutton = itemView.findViewById<Button>(R.id.PDFbutton)
        val DPPbtn = itemView.findViewById<Button>(R.id.DPPbtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var map: Map<String, Any> = mapData[mapData.keys.elementAt(position)] as Map<String, Any>

        val pdflink = map["pdfLink"].toString()
        val DPP = map["DppLink"].toString()
        val youtubelink = map["youtubeLink"].toString()

        Glide.with(holder.itemView).load("https://img.youtube.com/vi/$youtubelink/hqdefault.jpg").fitCenter().into(holder.imageView)

        holder.imageView.setOnClickListener{
            val intent2 = Intent(context, CustomUiActivity::class.java)
            intent2.putExtra("youtubelink",youtubelink)
            context.startActivity(intent2)
        }

        holder.DPPbtn.setOnClickListener {
            if(map["DppLink"]!=null){
                val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(DPP))
                context.startActivity(intent3)}
            else Toast.makeText(context,"DPP not available",Toast.LENGTH_SHORT).show()
        }

        holder.pdfbutton.setOnClickListener {
            if(map["pdfLink"]!=null){
                val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(pdflink))
                context.startActivity(intent3)}
            else Toast.makeText(context,"PDF not available",Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}