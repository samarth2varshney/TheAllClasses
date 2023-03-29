package com.example.theallclasses

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.theallclasses.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class Splash_screen : AppCompatActivity() {
    val db = Firebase.firestore
    val auth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name: String? = null



       GlobalScope.launch (Dispatchers.IO) {

           val Board = db.document("/Boards/boards")
           Board.get()
               .addOnSuccessListener { document ->
                   if (document != null) {
                       SharedData.Boardmap = document.data as Map<String, Any>
                       name = document.id
                       intent.putExtra("name", name)
                       SharedData.flag1=true
                       openActivity()
                   } else {
                       Log.d(ContentValues.TAG, "No such document")
                   }
               }
               .addOnFailureListener { exception ->
                   Log.d(ContentValues.TAG, "get failed with ", exception)
               }
       }

        GlobalScope.launch (Dispatchers.IO) {

            val JEE = db.document("/JEE/jee")
            JEE.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.JEEmap = document.data as Map<String, Any>
                        name = document.id
                        intent.putExtra("JEEmap", name)
                        SharedData.flag2=true
                        openActivity()
                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
        }
        GlobalScope.launch (Dispatchers.IO) {

            val NEET = db.document("/NEET/neet")
            NEET.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                       SharedData.NEETmap = document.data as Map<String, Any>
                        name = document.id
                        intent.putExtra("NEETmap", name)
                        SharedData.flag3=true
                        openActivity()
                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }

        }
        GlobalScope.launch (Dispatchers.IO) {
            val Teachertraningcourse = db.document("/TeacherTraningCourse/teachertraningcourse")
            Teachertraningcourse.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.TeacherTraningCoursemap = document.data as Map<String, Any>
                        name = document.id
                        intent.putExtra("TeacherTraningCoursemap", name)
                        SharedData.flag4=true
                        openActivity()
                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }

       }
    }

    private fun openActivity() {
        if(SharedData.flag1&&SharedData.flag2&&SharedData.flag3&&SharedData.flag4){
            if (auth.currentUser != null){
                val user = Firebase.auth.currentUser
                user?.let {
                    SharedData.uid=it.uid
                }
                startActivity(Intent(this , MainActivity::class.java))
            }
            else{
                startActivity(Intent(this, PhoneActivity::class.java))
            }
        }
    }
}