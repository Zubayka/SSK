package com.example.sskplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.sskplatform.ui.appeals.AppealsActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    val db = Firebase.firestore

    private lateinit var name: EditText
    private lateinit var password: EditText
    private lateinit var login: Button

    val usersCollectionRef = db.collection("Users")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        name = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)

        login.setOnClickListener{
            val username = name.text.toString()
            val password = password.text.toString()

            // Проверка наличия введенного имени пользователя
            usersCollectionRef
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        // Документ найден, сравнение пароля
                        val userDoc = documents.first()
                        val savedPassword = userDoc.getString("password")
                        if (savedPassword == password) {
                            // Пароль совпадает, вход
                            Toast.makeText(this, "Добро пожаловать, $username", Toast.LENGTH_SHORT).show()
                            Log.d("LOGIN ACTIVITY", username)
                            transition()
                        } else {
                            // Пароль не совпадает, ошибка
                            Toast.makeText(this, "Неверный пароль", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Пользователь не обнаружен
                        Toast.makeText(this, "Пользователь $username не обнаружен", Toast.LENGTH_SHORT).show()
                    }

                }
                .addOnFailureListener { exception ->
                    // Обработка ошибок, если запрос не удался
                    Toast.makeText(this, "Ошибка: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }

    }

    fun transition() {
        val intent = Intent(applicationContext, AppealsActivity::class.java)
        intent.putExtra("name", name.text.toString())
        startActivity (intent)
    }
}