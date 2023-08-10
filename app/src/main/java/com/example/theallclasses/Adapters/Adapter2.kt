package com.example.theallclasses.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theallclasses.ItemsData
import com.example.theallclasses.R

class Adapter2(private val mList: List<ItemsData>) : RecyclerView.Adapter<Adapter2.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.purchase_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsData = mList[position]

        holder.coursenameTV.text = ItemsData.coursename
        holder.coursepriceTV.text = ItemsData.courseprice

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val coursenameTV: TextView = itemView.findViewById(R.id.coursenameTV)
        val coursepriceTV: TextView = itemView.findViewById(R.id.coursepriceTV)
    }
}
