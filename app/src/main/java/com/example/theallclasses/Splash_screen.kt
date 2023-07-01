package com.example.theallclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.theallclasses.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Splash_screen : AppCompatActivity() {
    val db = Firebase.firestore
    val auth = FirebaseAuth.getInstance()
    var map: Map<String, Any>? = null
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.animate().apply {
            duration = 1000
            rotation(360f)
            flag6=true
            openActivity()
        }.start()

       GlobalScope.launch (Dispatchers.IO) {

           if(auth.currentUser!=null) {
               SharedData.uid = auth.currentUser!!.uid
               val courses = db.document("/users/${SharedData.uid}")
               courses.get()
                   .addOnSuccessListener { document ->
                       if (document != null) {
                           SharedData.Mycourses = document.data!!["mycourses"] as Map<String, Any>?
                           flag1 = true
                           openActivity()
                       }
                   }
           }

           val Board = db.document("/Boards/boards")
           Board.get()
               .addOnSuccessListener { document ->
                   if (document != null) {
                       //SharedData.Boardmap = document.data as Map<String, Any>
                       flag2 = true
                       openActivity()
                   }
               }

           val JEE = db.document("/JEE/JEE")
           JEE.get()
               .addOnSuccessListener { document ->
                   if (document != null) {
                       SharedData.JEEmap = document.data as Map<String, Any>
                       flag3 = true
                       openActivity()
                   }
               }

           val NEET = db.document("/NEET/neet")
           NEET.get()
               .addOnSuccessListener { document ->
                   if (document != null) {
                       SharedData.NEETmap = document.data as Map<String, Any>
                       flag4 = true
                       openActivity()
                   }
               }
       }
           GlobalScope.launch (Dispatchers.IO) {

           val docRef = db.document("/HeroSectionSliderImages/appslider") // TODO remove this and merge it in data base with others
           docRef.get()
               .addOnSuccessListener { document ->
                   if (document != null) {
                       map = document.data as Map<String,Any>
                       SharedData.imagename = map!!.keys.toTypedArray()
                   }
               }

           val courseImage = db.document("/CourseImage/image") // TODO remove this and merge it in data base with others
           courseImage.get()
               .addOnSuccessListener { document ->
                   if (document != null) {
                       SharedData.courseImage = document.data as Map<String, String>
                   }
               }
           val LiveAndMaterial = db.document("/LiveAndMaterial/LiveAndMaterail")
           LiveAndMaterial.get()
               .addOnSuccessListener { document ->
                   if (document != null) {
                       var tempmap = document.data as Map<String, Any>
                       SharedData.MaterialFragmentData = tempmap["Material"] as Map<String, Any>
                       SharedData.LiveFragmentData = tempmap["Live"] as Map<String, Any>
                       flag5=true
                       openActivity()
                   }
               }
       }

    }

    private fun openActivity() {
        if(flag1 && flag2 && flag3 && flag4 && flag5 && flag6){
            if (auth.currentUser != null){
                startActivity(Intent(this , MainActivity2::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
        }
    }

    companion object{
        var flag1=false
        var flag2=false
        var flag3=false
        var flag4=false
        var flag5=false
        var flag6=false
    }
}