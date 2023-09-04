package com.example.theallclasses

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.theallclasses.databinding.ActivityPurchaseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class PurchaseActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding: ActivityPurchaseBinding
    lateinit var courseName:String
    var location:String = ""
    var type:String = ""
    var startDate:String =""
    var endDate:String =""
    val auth = FirebaseAuth.getInstance()
    var originalCost = 0;
    var cost = 0
    var discount = 0.25
    var couponDiscount = 0.15
    var chechFlag = true
    var coupon = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        originalCost = intent.getIntExtra("cost",0)
        cost = (originalCost*(1-discount)).roundToInt()

        courseName = intent.getStringExtra("courseName").toString()
        location = intent.getStringExtra("location").toString()
        type = intent.getStringExtra("type").toString()
        startDate = intent.getStringExtra("startDate").toString()
        endDate = intent.getStringExtra("endDate").toString()

        binding.courseName.text = courseName
        binding.endDate.text = endDate
        binding.cost.text = originalCost.toString()
        binding.discount.text = (originalCost*discount).roundToInt().toString()
        binding.couponDiscount.text = 0.toString()
        binding.total.text = cost.toString()

        binding.apply.setOnClickListener {
            coupon = binding.coupon.text.toString()
            if(coupon.length>=3&&coupon[0]=='T'&&coupon[1]=='H'&&coupon[2]=='E'&&chechFlag){
                binding.couponDiscount.text = (originalCost*couponDiscount).roundToInt().toString()
                cost-= (originalCost*couponDiscount).roundToInt()
                binding.total.text = cost.toString()
                chechFlag = false
            }else if(chechFlag){
                Toast.makeText(this,"Invalid Coupon Code",Toast.LENGTH_SHORT).show()
            }
        }

        Checkout.preload(this@PurchaseActivity)

        binding.btnPayNow.setOnClickListener {
            payNow(cost, courseName)
        }
    }
    fun payNow(amount: Int, courseName: String?){
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_z4hn27x6bb4cZV")

        try {
            val options = JSONObject()
            options.put("name", "all THE Classes")
            options.put("description", courseName)
            options.put("currency", "INR")
            options.put("amount", amount*100)

            val retryObj = JSONObject()
            retryObj.put("enable", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val image: Int = R.drawable.logo
            checkout.setImage(image)

            checkout.open(this@PurchaseActivity, options)
        } catch (e: Exception) {
            Toast.makeText(this@PurchaseActivity,"Error in Payment: " + e.message, Toast.LENGTH_SHORT ).show()
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this@PurchaseActivity,"Payment Complete", Toast.LENGTH_SHORT ).show()
        val documentRef = FirebaseFirestore.getInstance().collection("users").document(auth.currentUser!!.uid)
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
                            "type" to type,
                            "referralCode" to coupon
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
                            "type" to type,
                            "referralCode" to coupon
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
    }


    override fun onPaymentError(p0: Int, p1: String?) {
//        binding.tvTransact.text = "Payment Failed: ${p1}"
        //binding.tvTransact.setTextColor(Color.RED)
    }
}