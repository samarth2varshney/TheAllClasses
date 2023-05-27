package com.example.theallclasses

import android.content.Context
import android.content.Intent
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

class ExploreAndBuyAdapter (private val context: Context, private val mapData: Map<String, Any>, private val mapWithName: Map<String, Any>) : RecyclerView.Adapter<ExploreAndBuyAdapter.ViewHolder>(){


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val keyTextView = itemView.findViewById<TextView>(R.id.keyTextView2)!!
        val imageView = itemView.findViewById<ImageView>(R.id.pic2)
        val explorebutton = itemView.findViewById<Button>(R.id.exploreId)
        val buybutton = itemView.findViewById<Button>(R.id.buyId)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.explore_and_buy_item, parent, false)
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
        holder.explorebutton.setOnClickListener {
            if (mapData[mapData.keys.elementAt(position)] is Map<*, *>){
                map2 = mapData[mapData.keys.elementAt(position)] as Map<String, Any>
                if (mapData.keys.elementAt(position)[0]!='t'){
                    mintent.putExtra("map", map2 as Serializable)
                    context.startActivity(mintent)
                }
                else{
                    val intent = Intent(context, CustomUiActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }

        holder.buybutton.setOnClickListener {

        }


    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}