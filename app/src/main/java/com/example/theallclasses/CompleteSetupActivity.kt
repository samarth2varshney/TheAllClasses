package com.example.theallclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.theallclasses.databinding.ActivityCompleteSetupBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CompleteSetupActivity : AppCompatActivity() {
    lateinit var binding: ActivityCompleteSetupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnsetup.setOnClickListener {
            val username = binding.etusername.text.toString()
            if(username.isEmpty()){
                Toast.makeText(this,"Please enter a valid username",Toast.LENGTH_SHORT).show()
                binding.etusername.text.clear()
            }
            else{
                SharedData.username = username
                val data1 = hashMapOf(
                    "board" to false,
                    "jee" to false,
                    "neet" to false,
                    "teachertrainingcourse" to false,
                    "username" to username
                )
                val db = Firebase.firestore
                val docRef = db.collection("users").document(SharedData.uid)
                docRef.set(data1)
                startActivity(Intent(this,MainActivity::class.java))
            }
        }

    }
}