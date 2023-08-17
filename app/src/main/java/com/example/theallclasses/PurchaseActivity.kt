package com.example.theallclasses

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.theallclasses.databinding.ActivityPurchaseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.math.roundToInt

class PurchaseActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding: ActivityPurchaseBinding
    lateinit var courseName:String
    var location:String = ""
    var type:String = ""
    var startDate:String =""
    var endDate:String =""
    var courseImage = ""
    val auth = FirebaseAuth.getInstance()
    var cost = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cost = intent.getIntExtra("cost",0)

        courseName = intent.getStringExtra("courseName").toString()
        location = intent.getStringExtra("location").toString()
        type = intent.getStringExtra("type").toString()
        startDate = intent.getStringExtra("startDate").toString()
        endDate = intent.getStringExtra("endDate").toString()
        courseImage = intent.getStringExtra("courseImage").toString()

        binding.courseName.text = courseName
        binding.endDate.text = endDate
        binding.cost.text = "₹ ${(cost)}"
        binding.textView35.text = "₹ ${(cost*0.25)}"
        binding.textView38.text = "₹ ${(cost*0.75)}"
        binding.textView39.text = "₹ ${0}"
        Glide.with(this).load(courseImage).fitCenter().into(binding.imageView5)

        Checkout.preload(this@PurchaseActivity)

        binding.button18.setOnClickListener {
            var check = binding.textView40.text
            if(check.length>=3&&check[0]=='T'&&check[1]=='H'&&check[2]=='E'){
                binding.textView39.text = "₹ ${(cost*0.10)}"
                cost = (cost*0.9).roundToInt()
                binding.cost.text = "₹ ${(cost)}"
                binding.textView35.text = "₹ ${(cost*0.25)}"
                binding.textView38.text = "₹ ${(cost*0.75)}"
            }
        }

        binding.btnPayNow.setOnClickListener {
            payNow((cost*0.75).roundToInt(), courseName)
        }
    }
    fun payNow(amount: Int, courseName: String?){

        val checkout = Checkout()
        checkout.setKeyID("rzp_test_z4hn27x6bb4cZV")

        try {
            val options = JSONObject()
            options.put("name", "The All Classes")
            options.put("description", courseName)
            options.put("currency", "INR")
            options.put("amount", amount*100)

            val retryObj = JSONObject()
            retryObj.put("enable", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            checkout.open(this@PurchaseActivity, options)
        } catch (e: Exception) {
            Toast.makeText(this@PurchaseActivity,"Error in Payment: " + e.message, Toast.LENGTH_SHORT ).show()
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this@PurchaseActivity,"Payment Complete", Toast.LENGTH_SHORT ).show()
        val documentRef = FirebaseFirestore.getInstance().collection("users").document(SharedData.uid!!)
        documentRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val data = documentSnapshot.data
                val currentDate = LocalDate.now()
                val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val formattedDate = currentDate.format(dateFormat)
                if (data != null) {
                    // Get the existing map
                    val existingMap = data["mycourses"] as? HashMap<String, HashMap<String, String>>
                    Log.d("course", data.toString())
                    if (existingMap?.get("0") != null) {
                        Log.d("course", existingMap.toString())
                        // Add new key-value pairs to the existing map
                        val innermap = hashMapOf(
                            "courseName" to courseName,
                            "date" to formattedDate,
                            "endDate" to endDate,
                            "startDate" to startDate,
                            "location" to location,
                            "type" to type
                        )
                        existingMap[existingMap.size.toString()] = innermap

                        // Update the document with the modified map
                        documentRef.update("mycourses", existingMap)
                            .addOnSuccessListener {
                                // Document update success
                                // You can perform any additional actions here
                                SharedData.Mycourses = existingMap
                                Log.d("course", "Updated mycourses")
                            }
                            .addOnFailureListener { e ->
                                // Document update failed
                                // Handle the error here
                                Log.d("course", "Failed to updated mycourses")
                            }
                    } else {
                        Log.d("course", "null")
                        val innermap = hashMapOf(
                            "courseName" to courseName,
                            "date" to formattedDate,
                            "endDate" to endDate,
                            "startDate" to startDate,
                            "location" to location,
                            "type" to type
                        )
                        data["mycourses"] = hashMapOf(
                            "0" to innermap
                        )

                        // Set the data in the Firestore document
                        documentRef.set(data)
                            .addOnSuccessListener {
                                // Document creation success
                                // You can perform any additional actions here
                                Log.d("course", "mycourses 0th added")
                            }
                            .addOnFailureListener { e ->
                                // Document creation failed
                                Log.d("course", "Failed to add mycourses 0th index")
                            }
                    }
                }

            }
        }
//        binding.tvTransact.text = "Payment ID: ${p0}"
        //binding.tvTransact.setTextColor(Color.GREEN)
        startActivity(Intent(this,MainActivity2::class.java))
    }


    override fun onPaymentError(p0: Int, p1: String?) {
//        binding.tvTransact.text = "Payment Failed: ${p1}"
        //binding.tvTransact.setTextColor(Color.RED)
    }
}