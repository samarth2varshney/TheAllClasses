package com.example.theallclasses
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.theallclasses.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var intent = intent

        val Boardmap = intent.getSerializableExtra("Boardmap") as? Map<String, Any>
        val JEEmap = intent.getSerializableExtra("JEEmap") as? Map<String, Any>
        val NEETmap = intent.getSerializableExtra("NEETmap") as? Map<String, Any>
        val TeacherTraningCoursemap = intent.getSerializableExtra("TeacherTraningCoursemap") as? Map<String, Any>

        val mintent = Intent(this, Recycler1::class.java)

        binding.textView.text = "${JEEmap!!.keys}"

       binding.Boardsbtn.setOnClickListener {
           mintent.putExtra("map", Boardmap as Serializable)
           startActivity(mintent)
       }

        binding.JEEbtn.setOnClickListener {
            mintent.putExtra("map", JEEmap as Serializable)
            startActivity(mintent)
        }

        binding.NEETbtn.setOnClickListener {
            mintent.putExtra("map", NEETmap as Serializable)
            startActivity(mintent)
        }

        binding.TeacherTraningbtn.setOnClickListener {
            mintent.putExtra("map", TeacherTraningCoursemap as Serializable)
            startActivity(mintent)
        }


    }
}