package com.example.theallclasses
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.theallclasses.databinding.ActivityMain2Binding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        SharedData.uid = sh.getString("uid", "")

        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationDrawer
        bottomNavigationView = binding.bottomNavigation

        bottomNavigationView.menu.findItem(R.id.navigation_home).isChecked = true;
        // Set up the toolbar
        setSupportActionBar(binding.toolbar)

        // Set up the ActionBarDrawerToggle
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar, // Pass the toolbar reference here
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        // Set up navigation item selected listener for the drawer
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
//                R.id.navigation_item1 -> {
//                    // Handle navigation item 1 click
//                    // Replace with your own logic
//                    replaceFragment(Item1Fragment())
//                }
//                R.id.navigation_item2 -> {
//                    // Handle navigation item 2 click
//                    // Replace with your own logic
//                    replaceFragment(Item2Fragment())
//                }
//                R.id.navigation_item3 -> {
//                    // Handle navigation item 3 click
//                    // Replace with your own logic
//                    replaceFragment(Item3Fragment())
//                }
            R.id.navigation_logout -> {
//                    // Handle navigation item 3 click
//                    // Replace with your own logic
//                    replaceFragment(Item3Fragment())
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this@MainActivity2, SignInActivity::class.java))
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        // Set up navigation item selected listener for the bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Handle home item click
                    // Replace with your own logic
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.navigation_live -> {
                    // Handle dashboard item click
                    // Replace with your own logic
                    replaceFragment(LiveFragment())
                    true
                }
                R.id.navigation_material -> {
                    replaceFragment(MaterialFragment())
                    true
                }
                R.id.navigation_courses -> {
                    // Handle notifications item click
                    // Replace with your own logic


//                    replaceFragment(CoursesFragment())
                    true
                }
                else -> false
            }
        }

        // Set the initial fragment
        val fragmentManager = supportFragmentManager // Use this if you're inside an activity
        val currentFragment = fragmentManager.findFragmentById(binding.frameLayout.id)

        if (currentFragment != HomeFragment()) {
            replaceFragment(HomeFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (WebviewFragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

}
