package com.example.sskplatform.ui.profile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sskplatform.LoginActivity
import com.example.sskplatform.R
import com.example.sskplatform.databinding.FragmentProfileBinding
import com.example.sskplatform.ui.appeals.AppealsActivity
import com.example.sskplatform.ui.violation.ViolationActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView
import java.util.concurrent.Executors


@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var button: Button
    private lateinit var docs: TextView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var name: TextView
    private lateinit var department: TextView
    private lateinit var position: TextView
    private lateinit var imageView: CircleImageView
    private lateinit var countView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        val username = intent.extras?.getString("name")
        Log.d("PROFILE ACTIVITY", username.toString())

        binding = FragmentProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = findViewById(R.id.menuProfile)

        bottomNavigationView.selectedItemId = R.id.navigation_profile

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_appeals -> {
                    val intent = Intent(this, AppealsActivity::class.java)
                    intent.putExtra("name", username)
                    item.setChecked(true)
                    startActivity(intent)
                    true
                }
                R.id.navigation_violation -> {
                    val intent = Intent(this, ViolationActivity::class.java)
                    intent.putExtra("name", username)
                    item.setChecked(true)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        name = binding.profileName
        department = binding.department
        position = binding.position
        imageView = binding.profileImage
        countView = binding.count

        button = binding.logout
        docs = binding.docsTransition

        readData()

        button.setOnClickListener {
            transition()
        }

        docs.setOnClickListener {
            openDocs()
        }

        val db = FirebaseFirestore.getInstance()

        val collectionRef = db.collection("Violations")

        collectionRef.whereEqualTo("author", username)
            .whereEqualTo("status", "Завершена")
            .get()
            .addOnSuccessListener { documents ->
                val count = documents.size()
                Log.d("Firestore", "Количество документов: $count")
                countView.text = count.toString()
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Ошибка при получении документов: $exception")
            }

    }

    private fun openDocs() {
        val intent = Intent(applicationContext, Docs::class.java)
        startActivity(intent)
    }

    private fun transition() {
        val transIntent = Intent(this, LoginActivity::class.java)
        startActivity(transIntent)
    }

    private fun readData() {
        val username = intent.extras?.getString("name")
        val db = FirebaseFirestore.getInstance()
        db.collection("Users")
            .whereEqualTo("username", username)
            .get()
            .addOnCompleteListener {
                val nameF = StringBuffer()
                if(it.isSuccessful) {
                    for(document in it.result!!){
                        nameF.append(document.data.getValue("name")).append(" ")
                    }
                    name.text = nameF
                }

                val areasF = StringBuffer()
                if(it.isSuccessful) {
                    for(document in it.result!!){
                        areasF.append(document.data.getValue("areas")).append(" ")
                    }
                    department.text = areasF
                }

                val positionF = StringBuffer()
                if(it.isSuccessful) {
                    for(document in it.result!!){
                        positionF.append(document.data.getValue("position")).append(" ")
                    }
                    position.text = positionF
                }

                val url = StringBuffer()
                if(it.isSuccessful) {
                    for(document in it.result!!){
                        url.append(document.data.getValue("image")).append(" ")
                    }
                }

                val executor = Executors.newSingleThreadExecutor()
                val handler = Handler(Looper.getMainLooper())
                var image: Bitmap?

                executor.execute{
                    url

                    try{
                        val `in` = java.net.URL(url.toString()).openStream()
                        image = BitmapFactory.decodeStream(`in`)
                        handler.post{
                            imageView.setImageBitmap(image)
                        }
                    }catch (e:java.lang.Exception){
                        e.printStackTrace()
                    }
                }
            }
    }
}