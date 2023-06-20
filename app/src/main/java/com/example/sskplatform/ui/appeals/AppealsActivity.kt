package com.example.sskplatform.ui.appeals

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sskplatform.R
import com.example.sskplatform.databinding.FragmentAppealsBinding
import com.example.sskplatform.ui.profile.ProfileActivity
import com.example.sskplatform.ui.violation.ViolationActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class AppealsActivity : AppCompatActivity() {

    private lateinit var binding: FragmentAppealsBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAppealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.extras?.getString("name")
        Log.d("APPEALS ACTIVITY", username.toString())

        binding.recyclerView.apply{
            layoutManager = LinearLayoutManager(this@AppealsActivity)
        }

        fetchData()

        bottomNavigationView = findViewById(R.id.menuAppeals)

        bottomNavigationView.setSelectedItemId(R.id.navigation_appeals)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_violation -> {
                    val intent = Intent(this, ViolationActivity::class.java)
                    item.setChecked(true)
                    intent.putExtra("name", username)
                    startActivity(intent)
                    true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    item.setChecked(true)
                    intent.putExtra("name", username)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun fetchData() {
        val username = intent.extras?.getString("name")
        FirebaseFirestore.getInstance().collection("Violations")
            .whereEqualTo("author", username)
            .whereEqualTo("status", "Отправлено")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val violation = documents.toObjects(ViolationModel::class.java)
                    binding.recyclerView.adapter = ViolationAdapter(this, violation)
                }
            }
            .addOnFailureListener{

                showToast("An error occured: ${it.localizedMessage}")
            }
    }

}