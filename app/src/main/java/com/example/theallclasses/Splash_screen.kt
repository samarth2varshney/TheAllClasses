package com.example.theallclasses

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.theallclasses.databinding.ActivitySplashScreenBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.Serializable

class Splash_screen : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name: String? = null

        val intent = Intent(this, MainActivity::class.java)

       GlobalScope.launch (Dispatchers.IO) {

           val Board = db.document("/Boards/boards")
           Board.get()
               .addOnSuccessListener { document ->
                   if (document != null) {
                       Boardmap = document.data as Map<String, Any>
                       name = document.id
                       intent.putExtra("name", name)
                       flag1=true
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
                        JEEmap = document.data as Map<String, Any>
                        name = document.id
                        intent.putExtra("JEEmap", name)
                        flag2=true
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
                        NEETmap = document.data as Map<String, Any>
                        name = document.id
                        intent.putExtra("NEETmap", name)
                        flag3=true
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
                        TeacherTraningCoursemap = document.data as Map<String, Any>
                        name = document.id
                        intent.putExtra("TeacherTraningCoursemap", name)
                        flag4=true
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
        val intent = Intent(this, MainActivity::class.java)
        if(flag1&&flag2&&flag3&&flag4){
            intent.putExtra("Boardmap", Boardmap as Serializable)
            intent.putExtra("JEEmap", JEEmap as Serializable)
            intent.putExtra("NEETmap", NEETmap as Serializable)
            intent.putExtra("TeacherTraningCoursemap", TeacherTraningCoursemap as Serializable)
            startActivity(intent)
        }
    }

    companion object{
    var Boardmap: Map<String, Any>? = null
    var JEEmap: Map<String, Any>? = null
    var NEETmap: Map<String, Any>? = null
    var TeacherTraningCoursemap: Map<String, Any>? = null

        var flag1=false
        var flag2=false
        var flag3=false
        var flag4=false

    }
}