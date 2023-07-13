package com.example.theallclasses

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.Serializable

class BuyedCourseAdapter(
    private val context: Context,
    private val mapData: Map<String, Any>
) : RecyclerView.Adapter<BuyedCourseAdapter.ViewHolder>(){

    class ViewHolder(itemView: View, activityview: View) : RecyclerView.ViewHolder(itemView) {
        val keyTextView = itemView.findViewById<TextView>(R.id.keyTextView1)!!
        val imageView = itemView.findViewById<ImageView>(R.id.pic1)
        val explorebutton = itemView.findViewById<Button>(R.id.studybt)
        val exploreAndBuyBG = itemView.findViewById<ConstraintLayout>(R.id.horizontalItemBG)
        val layout = activityview.findViewById<FrameLayout>(R.id.frame_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.buyed_course_item, parent, false)
        val activityview = LayoutInflater.from(parent.context).inflate(R.layout.activity_main2, parent, false)
        return ViewHolder(view,activityview)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position % 5 == 0) {
            holder.exploreAndBuyBG.setBackgroundColor(
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
            holder.exploreAndBuyBG.setBackgroundColor(
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
            holder.exploreAndBuyBG.setBackgroundColor(
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
            holder.exploreAndBuyBG.setBackgroundColor(
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
            holder.exploreAndBuyBG.setBackgroundColor(
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




        val map: Map<String, Any> = mapData[mapData.keys.elementAt(position)] as Map<String, Any>

        val coursename = map["name"].toString()
        val courseimage = map["courseImage"].toString()

        holder.keyTextView.text = coursename
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
        }else{
            holder.explorebutton.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}