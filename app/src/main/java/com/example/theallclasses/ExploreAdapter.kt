package com.example.theallclasses

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
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

class ExploreAdapter(
    private val context: Context,
    private val mapData: Map<String, Any>,
    val showBuyButton: Boolean
) : RecyclerView.Adapter<ExploreAdapter.ViewHolder>(){

    class ViewHolder(itemView: View, activityview: View) : RecyclerView.ViewHolder(itemView) {
        val keyTextView = itemView.findViewById<TextView>(R.id.keyTextView2)!!
        val imageView = itemView.findViewById<ImageView>(R.id.pic2)
        val explorebutton = itemView.findViewById<Button>(R.id.exploreId)
        val buybutton = itemView.findViewById<Button>(R.id.buyId)
        val courseInfo = itemView.findViewById<TextView>(R.id.courseInfo)
        val timeofcourse = itemView.findViewById<TextView>(R.id.timeofcourse)
        val exploreAndBuyBG = itemView.findViewById<ConstraintLayout>(R.id.exploreAndBuyBG)
        val layout = activityview.findViewById<FrameLayout>(R.id.frame_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.explore_and_buy_item, parent, false)
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

        holder.explorebutton.setOnClickListener {
            val fragment = Chapter()
            val args = Bundle()
            args.putSerializable("map", map["paidContent"] as Serializable)
            fragment.arguments = args

            val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(holder.layout.id, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        if(showBuyButton)
        holder.buybutton.setOnClickListener {
            val intent = Intent(context, PurchaseActivity::class.java)
            if(cousercost!="null"){
                intent.putExtra("courseName" ,mapData.keys.elementAt(position))
                intent.putExtra("cost", cousercost.toInt())
                context.startActivity(intent)
            }
        }
        else
            holder.buybutton.visibility = View.GONE

    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}