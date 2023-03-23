package com.example.theallclasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter (private val mapData: Map<String, Any>) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int){

        }
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class ViewHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val keyTextView = itemView.findViewById<TextView>(R.id.keyTextView)!!
        //val valueTextView = itemView.findViewById<TextView>(R.id.valueTextView)!!

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.map_item, parent, false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val key = mapData.keys.elementAt(position)
        val value = mapData[key]
        holder.keyTextView.text = key
    }

    //Override getItemCount
    override fun getItemCount(): Int {
        return mapData.size
    }
}