package com.example.theallclasses.Adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theallclasses.CustomUiActivity
import com.example.theallclasses.R

class Show_live_Adapter(private val context: Context, private val mapData: Map<String, Any>) : RecyclerView.Adapter<Show_live_Adapter.ViewHolder>(){

    class ViewHolder(itemView: View, activityview: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.liveVideo)
        val textView = itemView.findViewById<TextView>(R.id.textView28)
        val textView2 = itemView.findViewById<TextView>(R.id.textView29)
        val layout = activityview.findViewById<FrameLayout>(R.id.frame_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_live_item, parent, false)
        val activityview = LayoutInflater.from(parent.context).inflate(R.layout.activity_main2, parent, false)
        return ViewHolder(view,activityview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val youtubeLink = mapData[mapData.keys.elementAt(position)].toString()
        val zoomMeeting = mapData[mapData.keys.elementAt(position)].toString()

        if(mapData.keys.elementAt(position).toString()[0] == 'y'){
            Glide.with(holder.itemView).load("https://img.youtube.com/vi/$youtubeLink/hqdefault.jpg").fitCenter().into(holder.imageView)
            holder.textView.visibility = View.GONE
            holder.textView2.visibility = View.GONE
        }else{
            //
            holder.textView.text = zoomMeeting
            holder.imageView.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            if(mapData.keys.elementAt(position).toString()[0] == 'y') {
                val intent2 = Intent(context, CustomUiActivity::class.java)
                intent2.putExtra("youtubelink", youtubeLink)
                context.startActivity(intent2)
            }else {
                val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(zoomMeeting))
                context.startActivity(intent3)
            }
        }

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}