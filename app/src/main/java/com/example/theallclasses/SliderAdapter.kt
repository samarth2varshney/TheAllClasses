package com.example.theallclasses

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage
import com.smarteist.autoimageslider.SliderViewAdapter
import java.io.File

class SliderAdapter(imageUrl: Array<String>?) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>()  {

    var sliderList: Array<String>? = imageUrl

    override fun getCount(): Int {
        return sliderList!!.size
    }
    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapter.SliderViewHolder {

        val inflate: View = LayoutInflater.from(parent!!.context).inflate(R.layout.slider_item, null)

        return SliderViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapter.SliderViewHolder?, position: Int) {

        if (viewHolder != null) {
            val storageRef = FirebaseStorage.getInstance().reference.child("images/${sliderList!![position]}")

            val localfile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                viewHolder.imageView.setImageBitmap(bitmap)
            }.addOnFailureListener {

            }
        }
    }

    class SliderViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {

        var imageView: ImageView = itemView!!.findViewById(R.id.myimage)
    }

}