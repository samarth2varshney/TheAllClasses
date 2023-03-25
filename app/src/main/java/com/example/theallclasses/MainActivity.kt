package com.example.theallclasses
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.theallclasses.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        var intent = intent

//        val Boardmap = intent.getSerializableExtra("Boardmap") as? Map<String, Any>
//        val JEEmap = intent.getSerializableExtra("JEEmap") as? Map<String, Any>
//        val NEETmap = intent.getSerializableExtra("NEETmap") as? Map<String, Any>
//        val TeacherTraningCoursemap = intent.getSerializableExtra("TeacherTraningCoursemap") as? Map<String, Any>

        val mintent = Intent(this, Recycler1::class.java)

       binding.Boardsbtn.setOnClickListener {
           mintent.putExtra("map", SharedData.Boardmap as Serializable)
           startActivity(mintent)
       }

        binding.JEEbtn.setOnClickListener {
            mintent.putExtra("map", SharedData.JEEmap as Serializable)
            startActivity(mintent)
        }

        binding.NEETbtn.setOnClickListener {
            mintent.putExtra("map", SharedData.NEETmap as Serializable)
            startActivity(mintent)
        }

        binding.TeacherTraningbtn.setOnClickListener {
            mintent.putExtra("map", SharedData.TeacherTraningCoursemap as Serializable)
            startActivity(mintent)
        }

        binding.signOutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, PhoneActivity::class.java))
        }
    }

}