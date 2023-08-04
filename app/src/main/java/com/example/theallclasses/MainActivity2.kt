package com.example.theallclasses

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.theallclasses.databinding.ActivityMain2Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        SharedData.uid = sh.getString("uid", "")

        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationDrawer
        bottomNavigationView = binding.bottomNavigation

        GlobalScope.launch (Dispatchers.IO) {
            val socialMediaLinks = db.document("/socialMediaLinks/socialMediaLinks")
            socialMediaLinks.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        SharedData.socialMediaLinks = document.data as Map<String, Any>
                        intializenavigationView()
                    }
                }
        }

        bottomNavigationView.menu.findItem(R.id.navigation_home).isChecked = true;
        setSupportActionBar(binding.toolbar)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val headerView: View = navigationView.getHeaderView(0)
        if (auth.currentUser != null) headerView.findViewById<TextView>(R.id.profile_name).text = auth.currentUser!!.displayName
        if (auth.currentUser != null) headerView.findViewById<TextView>(R.id.profile_email).text = auth.currentUser!!.email

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment(), true)
                    true
                }
                R.id.navigation_live -> {
                    replaceFragment(LiveFragment(), false)
                    true
                }
                R.id.navigation_material -> {
                    replaceFragment(MaterialFragment(), false)
                    true
                }
                R.id.navigation_courses -> {
                    replaceFragment(CoursesFragment(), false)
                    true
                }
                else -> false
            }
        }

        replaceFragment(HomeFragment(), true)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun intializenavigationView() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mycourses -> {
                    replaceFragment(CoursesFragment(),false)
                }
                R.id.material->{
                    replaceFragment(MaterialFragment(), false)
                }
                R.id.live->{
                    replaceFragment(MaterialFragment(), false)
                }
                R.id.Blog->{
                    val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(SharedData.socialMediaLinks!!["blogLink"].toString()))
                    startActivity(intent3)
                }
                R.id.Motivation->{
                    val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(SharedData.socialMediaLinks!!["motivationLink"].toString()))
                    startActivity(intent3)
                }
                R.id.offline->{
                    val fragment = OfflineMode()
                    val fragmentManager: FragmentManager =
                        (this as AppCompatActivity).supportFragmentManager
                    val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                    transaction.replace(binding.frameLayout.id, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                R.id.hometution->{
                    val fragment = HomeTuitionFragment()
                    val fragmentManager: FragmentManager =
                        (this as AppCompatActivity).supportFragmentManager
                    val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                    transaction.replace(binding.frameLayout.id, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                R.id.contactus->{
                    openMailApp("theallclasses.vikram@gmail.com", "", "")
                }
                R.id.insta->{
                    val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(SharedData.socialMediaLinks!!["instagram"].toString()))
                    startActivity(intent3)
                }
                R.id.telegram->{
                    val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(SharedData.socialMediaLinks!!["telegram"].toString()))
                    startActivity(intent3)
                }
                R.id.youtube->{
                    val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(SharedData.socialMediaLinks!!["youtube"].toString()))
                    startActivity(intent3)
                }
                R.id.twitter->{
                    val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(SharedData.socialMediaLinks!!["twitter"].toString()))
                    startActivity(intent3)
                }
                R.id.facebook->{
                    val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(SharedData.socialMediaLinks!!["facebook"].toString()))
                    startActivity(intent3)
                }
                R.id.terms_conditions->{
                    val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(SharedData.socialMediaLinks!!["terms&conditions"].toString()))
                    startActivity(intent3)
                }
                R.id.navigation_logout -> {
                    val sharedPreferences =
                        getSharedPreferences("MySharedPref", MODE_PRIVATE)
                    val myEdit = sharedPreferences.edit()
                    myEdit.putString("uid", "")
                    myEdit.apply()
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this@MainActivity2, SignInActivity::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        binding.whatsapp.setOnClickListener {
            val intent3 = Intent(Intent.ACTION_VIEW, Uri.parse(SharedData.socialMediaLinks!!["whatsapp"].toString()))
            startActivity(intent3)
        }

    }

    private fun replaceFragment(fragment: Fragment, flag: Boolean) {

        if(flag || auth.currentUser!=null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.frameLayout.id, fragment)
                .addToBackStack(null)
                .commit()
        }else{
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if (WebviewFragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    fun openMailApp(recipient: String, subject: String, body: String, cc: String = "", bcc: String = "") {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            putExtra(Intent.EXTRA_CC, cc)
            putExtra(Intent.EXTRA_BCC, bcc)
        }
        startActivity(intent)
    }

}
