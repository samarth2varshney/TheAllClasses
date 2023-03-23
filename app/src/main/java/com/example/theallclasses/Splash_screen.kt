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

        var Boardmap: Map<String, Any>? = null
        var JEEmap: Map<String, Any>? = null
        var NEETmap: Map<String, Any>? = null
        var TeacherTraningCoursemap: Map<String, Any>? = null
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
                        intent.putExtra("Boardmap", Boardmap as Serializable)

                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }

            val JEE = db.document("/JEE/jee")
            JEE.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        JEEmap = document.data as Map<String, Any>
                        name = document.id
                        intent.putExtra("JEEmap", name)
                        intent.putExtra("JEEmap", JEEmap as Serializable)

                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }

            val NEET = db.document("/NEET/neet")
            NEET.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        NEETmap = document.data as Map<String, Any>
                        name = document.id
                        intent.putExtra("NEETmap", name)
                        intent.putExtra("NEETmap", NEETmap as Serializable)

                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }
            val Teachertraningcourse = db.document("/TeacherTraningCourse/teachertraningcourse")
            Teachertraningcourse.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        TeacherTraningCoursemap = document.data as Map<String, Any>
                        name = document.id
                        intent.putExtra("TeacherTraningCoursemap", name)
                        intent.putExtra("TeacherTraningCoursemap", TeacherTraningCoursemap as Serializable)
                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }

       }



        binding.button5.setOnClickListener {

            startActivity(intent)
        }

    }
}