package com.example.theallclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.theallclasses.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    var auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinner2.visibility = View.GONE
        binding.transparentImage.visibility = View.GONE

        binding.llSignIn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignIn.setOnClickListener {

            val email = binding.etEmail1.text.toString()
            val password = binding.etPassword1.text.toString()

            if (email.isEmpty()) {
                binding.etEmail1.error = "Email is required."
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.etPassword1.error = "Password is required."
                return@setOnClickListener
            }

            if (password.length<7){
                binding.etPassword1.error = "Password should be greater than 7 words"
                return@setOnClickListener
            }

            binding.spinner2.visibility = View.VISIBLE
            binding.transparentImage.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "Authentication successfull.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, MainActivity2::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        binding.spinner2.visibility = View.GONE
                        binding.transparentImage.visibility = View.GONE
                        Toast.makeText(baseContext, "Don't have a account signup", Toast.LENGTH_SHORT).show()
                    }
                }

        }
        binding.tvFP.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {

            val sharedPreferences =
                getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val myEdit = sharedPreferences.edit()
            myEdit.putString("uid", currentUser.uid)
            myEdit.apply()

            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
}
