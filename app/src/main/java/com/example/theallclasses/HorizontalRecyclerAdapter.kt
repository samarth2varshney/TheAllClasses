package com.example.theallclasses

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.Serializable

class HorizontalRecyclerAdapter(private val context: Context, private val mapData: Map<String, Map<String, Any>?>) : RecyclerView.Adapter<HorizontalRecyclerAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val keyTextView = itemView.findViewById<TextView>(R.id.keyTextView1)!!
        val imageView = itemView.findViewById<ImageView>(R.id.pic1)
        val horizontalItemBG = itemView.findViewById<ConstraintLayout>(R.id.horizontalItemBG)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position % 5 == 0) {
            holder.horizontalItemBG.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.LightSkyBlue
                )
            )
            holder.keyTextView.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.RoyalBlue
                )
            )
        }
        else if (position % 5 == 1) {
            holder.horizontalItemBG.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.SalmonPink
                )
            )
            holder.keyTextView.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.Rojo
                )
            )
        }
        else if (position % 5 == 2) {
            holder.horizontalItemBG.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.Mauve
                )
            )
            holder.keyTextView.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.BlueViolet
                )
            )
        }
        else if (position % 5 == 3) {
            holder.horizontalItemBG.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.Timberwolf
                )
            )
            holder.keyTextView.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.DavyGray
                )
            )
        }
        else if (position % 5 == 4) {
            holder.horizontalItemBG.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.Aquamarine
                )
            )
            holder.keyTextView.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.PigmentGreen
                )
            )
        }


        holder.keyTextView.text = mapData.keys.elementAt(position)
        Glide.with(holder.itemView).load(SharedData.courseImage!![mapData.keys.elementAt(position)]).fitCenter().into(holder.imageView)

        val mintent = Intent(context, ExploreAndBuy::class.java)
        holder.itemView.setOnClickListener {
            mintent.putExtra("map", mapData[mapData.keys.elementAt(position)] as Serializable)
                context.startActivity(mintent)
        }
    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}