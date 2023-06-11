package com.example.theallclasses

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.theallclasses.databinding.ActivityPurchaseBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PurchaseActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding: ActivityPurchaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Checkout.preload(this@PurchaseActivity)

        binding.btnPayNow.setOnClickListener {
            payNow(500)
        }
    }
    fun payNow(amount: Int){
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_z4hn27x6bb4cZV")

        try {
            val options = JSONObject()
            options.put("name", "The All Classes")
            options.put("description", "Buy this course")
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

    override fun onPaymentSuccess(p0: String?) {
        binding.tvTransact.text = "Payment ID: ${p0}"
        binding.tvTransact.setTextColor(Color.GREEN)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        binding.tvTransact.text = "Payment Failed: ${p1}"
        binding.tvTransact.setTextColor(Color.RED)
    }
}