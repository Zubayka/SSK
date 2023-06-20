package com.example.sskplatform

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sskplatform.databinding.EnterScreenBinding
import com.example.sskplatform.ui.appeals.AppealsActivity
import com.example.sskplatform.ui.profile.ProfileActivity
import com.example.sskplatform.ui.violation.ViolationActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigateActivity : AppCompatActivity() {

    private lateinit var binding: EnterScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = EnterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.navView

        val text = intent.extras?.getString("name")
        Log.d("NAVIGATION ACTIVITY", text.toString())

        val intentViolation = Intent(applicationContext, ViolationActivity::class.java)
        intentViolation.putExtra("name", text)
        startActivity (intentViolation)

        val intentProfile = Intent(applicationContext, ProfileActivity::class.java)
        intentProfile.putExtra("name", text)
        startActivity (intentProfile)

        val intentAppeals = Intent(applicationContext, AppealsActivity::class.java)
        intentAppeals.putExtra("name", text)
        startActivity (intentAppeals)

        val navController = findNavController(R.id.enter_screen)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_appeals, R.id.navigation_violation, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
