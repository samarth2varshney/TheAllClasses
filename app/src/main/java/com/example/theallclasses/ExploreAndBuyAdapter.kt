package com.example.theallclasses

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.Serializable

class ExploreAndBuyAdapter (private val context: Context, private val mapData: Map<String, Any>, private val mapWithName: Map<String, Any>) : RecyclerView.Adapter<ExploreAndBuyAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val keyTextView = itemView.findViewById<TextView>(R.id.keyTextView2)!!
        val imageView = itemView.findViewById<ImageView>(R.id.pic2)
        val explorebutton = itemView.findViewById<Button>(R.id.exploreId)
        val buybutton = itemView.findViewById<Button>(R.id.buyId)
        val courseInfo = itemView.findViewById<TextView>(R.id.courseInfo)
        val timeofcourse = itemView.findViewById<TextView>(R.id.timeofcourse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.explore_and_buy_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val map: Map<String, Any> = mapWithName[mapWithName.keys.elementAt(position)] as Map<String, Any>
        var map2: Map<String, Any> = mapData[mapData.keys.elementAt(position)] as Map<String, Any>

        val coursename = map["name"].toString()
        val courseimage = map["image"].toString()
        val cousercost = map["cost"].toString()
        val coursetime = map["time"].toString()

        holder.keyTextView.text = coursename
        holder.timeofcourse.text = coursetime
        Glide.with(holder.itemView).load(courseimage).fitCenter().into(holder.imageView)

        // text with horizontal cut
        val spannableString = SpannableString(map["originalCost"].toString())
        spannableString.setSpan(StrikethroughSpan(), 0, map["originalCost"].toString().length, 0)
        holder.courseInfo.append(spannableString)
        holder.courseInfo.append("   ")

        holder.courseInfo.append(cousercost)

        val mintent = Intent(context, Recycler1::class.java)
        holder.explorebutton.setOnClickListener {
            mintent.putExtra("map", map2 as Serializable)
            context.startActivity(mintent)
        }

        holder.buybutton.setOnClickListener {
            val intent = Intent(context, PurchaseActivity::class.java)
            if(cousercost!="null"){
                intent.putExtra("courseName" ,mapData.keys.elementAt(position))
                intent.putExtra("cost", cousercost.toInt())
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}