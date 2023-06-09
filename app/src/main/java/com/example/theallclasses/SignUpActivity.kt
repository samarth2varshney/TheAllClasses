package com.example.theallclasses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.theallclasses.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth //for accessing Firebase features
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            val name = binding.etName2.text.toString()
            val email = binding.etEmail2.text.toString()
            val password = binding.etPassword2.text.toString()
            val cpassword = binding.etConfirmPass2.text.toString()

            if (name.isEmpty()) {
                binding.etName2.error = "Name is required."
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.etEmail2.error = "Email is required."
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword2.error = "Password is required."
                return@setOnClickListener
            }
            if (cpassword.isEmpty()) {
                binding.etConfirmPass2.error = "Password Confirmation is required."
                return@setOnClickListener
            }
            if (cpassword != password) {
                binding.etConfirmPass2.error = "Password doesn't match"
                return@setOnClickListener
            }
            if (password.length<7){
                binding.etConfirmPass2.error = "Password should be greater than 7 words"
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()

                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { profileUpdateTask ->
                                if (profileUpdateTask.isSuccessful) {
                                    // Display name updated successfully
                                    Toast.makeText(
                                        baseContext, "Sign Up and Authentication successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    SharedData.uid = user.uid
//                                    SharedData.username = name
//                                    SharedData.email = email
                                    val collectionRef = FirebaseFirestore.getInstance().collection("users")
                                    val documentRef = collectionRef.document(user.uid)

                                    // Create the data for the document
                                    val data = hashMapOf(
                                        "username" to name,
                                        "email" to email,
                                        "mycourses" to hashMapOf<String, String>()
                                    )
                                    SharedData.Mycourses = hashMapOf<String, String>()
                                    // Set the data in the Firestore document
                                    documentRef.set(data)
                                        .addOnSuccessListener {
                                            // Document creation success
                                            // You can perform any additional actions here
                                            goToMain()
                                        }
                                        .addOnFailureListener { e ->
                                            // Document creation failed
                                            // Handle the error here
                                        }

                                } else {
                                    // Display name update failed
                                    Toast.makeText(
                                        baseContext, "Failed to update display name.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        // If sign up fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
    }

    fun goToMain() {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
        finish()
    }
}