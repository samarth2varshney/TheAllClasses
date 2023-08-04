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
import com.google.firebase.auth.FirebaseAuth
import java.io.Serializable

val auth = FirebaseAuth.getInstance()

class ExploreAndBookseatAdapter(
    private val context: Context,
    private val mapData: Map<String, Any>,
    val bookSeat: Boolean,
    val location: String,
    val type: String
) : RecyclerView.Adapter<ExploreAndBookseatAdapter.ViewHolder>(){

    class ViewHolder(itemView: View, activityview: View) : RecyclerView.ViewHolder(itemView) {
        val keyTextView = itemView.findViewById<TextView>(R.id.keyTextView2)!!
        val imageView = itemView.findViewById<ImageView>(R.id.pic2)
        val explorebutton = itemView.findViewById<Button>(R.id.exploreId)
        val buybutton = itemView.findViewById<Button>(R.id.buyId)
        val courseInfo = itemView.findViewById<TextView>(R.id.courseInfo)
        val timeofcourse = itemView.findViewById<TextView>(R.id.timeofcourse)
        val exploreAndBuyBG = itemView.findViewById<ConstraintLayout>(R.id.exploreAndBuyBG)
        val layout = activityview.findViewById<FrameLayout>(R.id.frame_layout)
        val image = itemView.findViewById<ImageView>(R.id.imageView4)
        val durationtext = itemView.findViewById<TextView>(R.id.textView9)
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
            holder.exploreAndBuyBG.clipToOutline = true
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
            holder.exploreAndBuyBG.clipToOutline = true
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
            holder.exploreAndBuyBG.clipToOutline = true
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
            holder.exploreAndBuyBG.clipToOutline = true
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
            holder.exploreAndBuyBG.clipToOutline = true
        }
        holder.image.visibility = ImageView.INVISIBLE

        val map: Map<String, Any> = mapData[mapData.keys.elementAt(position)] as Map<String, Any>

        val coursename:String
        if(map["name"].toString()==null){
            coursename = mapData.keys.elementAt(position)
        }
        else{
            coursename = map["name"].toString()
        }
        val courseimage = map["courseImage"].toString()
        val cousercost = map["cost"].toString()

        if(map["seatsLeft"]!=null){
        val seatsLeft = map["seatsLeft"].toString()
        holder.timeofcourse.textSize = 16f
        holder.timeofcourse.append("Seats Left  ")
        holder.timeofcourse.append(seatsLeft)}

        holder.keyTextView.text = coursename
        Glide.with(holder.itemView).load(courseimage).fitCenter().into(holder.imageView)

        // text with horizontal cut
        val spannableString = SpannableString(map["originalCost"].toString())
        spannableString.setSpan(StrikethroughSpan(), 0, map["originalCost"].toString().length, 0)
        holder.courseInfo.append(spannableString)
        holder.courseInfo.append("   ")
        holder.courseInfo.append(cousercost)

        holder.durationtext.append(map["startDate"].toString())
        holder.durationtext.append(" to ")
        holder.durationtext.append(map["endDate"].toString())

        holder.explorebutton.setOnClickListener {
                val fragment = CourseDetails()
                val args = Bundle()
                args.putSerializable("map", map as Serializable)
                args.putString("location",location)
                args.putString("type",type)
                args.putString("startDate",map["startDate"].toString())
                args.putString("endDate",map["endDate"].toString())
                fragment.arguments = args
                val fragmentManager: FragmentManager = (context as AppCompatActivity).supportFragmentManager
                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                transaction.replace(holder.layout.id, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
        }

        if(bookSeat)
            holder.buybutton.text = "Book A Seat"

        val myButton = holder.buybutton
        val layoutParams = myButton.layoutParams
        layoutParams.width = dpToPx(110,myButton.context) // Convert dp to pixels
        myButton.layoutParams = layoutParams

        holder.buybutton.setOnClickListener {
            val intent = Intent(context, PurchaseActivity::class.java)
            if (auth.currentUser != null && cousercost!="null"){
                intent.putExtra("courseName" ,mapData.keys.elementAt(position))
                intent.putExtra("cost", cousercost.toInt())
                intent.putExtra("location",location)
                intent.putExtra("type",type)
                intent.putExtra("startDate",map["startDate"].toString())
                intent.putExtra("endDate",map["endDate"].toString())
                context.startActivity(intent)
            }
            else{
                val intent1 = Intent(context, SignInActivity::class.java)
                context.startActivity(intent1)
            }
        }

    }
    fun dpToPx(dp: Int, context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    override fun getItemCount(): Int {
        return mapData.size
    }
}