package com.example.theallclasses

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.theallclasses.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    var dateOfBirth = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.transparentImage2.visibility = View.GONE
        binding.spinner3.visibility = View.GONE

        var gender = "Male"
        val languages = resources.getStringArray(R.array.programming_languages)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_menu, languages)
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autocompleteTV.setAdapter(arrayAdapter)

        autocompleteTV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedLanguage = parent.getItemAtPosition(position) as String
                gender = selectedLanguage
            }

        binding.dateOfBirth.setOnClickListener{
            showDatePickerDialog()
        }

        auth = Firebase.auth

        binding.llSignUp.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            val name = binding.etName2.text.toString()
            val Class = binding.etClass.text.toString()
            val city = binding.etCity.text.toString()
            val state = binding.etState.text.toString()
            val number = binding.etnumber.text.toString()
            val email = binding.etEmail2.text.toString()
            val password = binding.etPassword2.text.toString()
            val cpassword = binding.etConfirmPass2.text.toString()


            if (name.isEmpty()) {
                binding.etName2.error = "Name is required."
                return@setOnClickListener
            }
            if (Class.isEmpty()) {
                binding.etClass.error = "class is required."
                return@setOnClickListener
            }
            if(city.isEmpty()){
                binding.etCity.error = "state is required."
                return@setOnClickListener
            }
            if (state.isEmpty()) {
                binding.etState.error = "state is required."
                return@setOnClickListener
            }
            if (dateOfBirth.isEmpty()) {
                binding.dateOfBirth.error = "Date of Birth is required."
                return@setOnClickListener
            }
            if (number.isEmpty()) {
                binding.etnumber.error = "Number is required"
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
            if (password.length < 7) {
                binding.etConfirmPass2.error = "Password should be greater than 7 words"
                return@setOnClickListener
            }

            binding.spinner3.visibility = View.VISIBLE
            binding.transparentImage2.visibility = View.VISIBLE

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
                                    val sharedPreferences =
                                        getSharedPreferences("MySharedPref", MODE_PRIVATE)
                                    val myEdit = sharedPreferences.edit()

                                    myEdit.putString("uid", user.uid)
                                    myEdit.apply()

                                    val collectionRef =
                                        FirebaseFirestore.getInstance().collection("users")
                                    val documentRef = collectionRef.document(user.uid)

                                    var testmap: HashMap<String, HashMap<String, String>>? =
                                        HashMap<String, HashMap<String, String>>()
                                    val data = hashMapOf(
                                        "username" to name,
                                        "gender" to gender,
                                        "date_of_birth" to dateOfBirth,
                                        "class" to Class,
                                        "city" to city,
                                        "state" to state,
                                        "number" to number,
                                        "email" to email,
                                        "mycourses" to testmap,
                                    )

                                    documentRef.set(data)
                                        .addOnSuccessListener {
                                            goToMain()
                                        }
                                        .addOnFailureListener { e ->
                                        }

                                } else {
                                    Toast.makeText(
                                        baseContext, "Failed to update display name.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        binding.spinner3.visibility = View.GONE
                        binding.transparentImage2.visibility = View.GONE

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

    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            R.style.CustomDatePickerDialog,
            DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                // Handle the selected date here
                dateOfBirth = "$dayOfMonth/${monthOfYear + 1}/$year"
                binding.dateOfBirth.append("    ")
                binding.dateOfBirth.append(dateOfBirth)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

}