@file:Suppress("DEPRECATION")

package com.example.sskplatform.ui.violation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sskplatform.R
import com.example.sskplatform.databinding.FragmentViolationBinding
import com.example.sskplatform.ui.appeals.AppealsActivity
import com.example.sskplatform.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class ViolationActivity : AppCompatActivity() {
    private lateinit var binding: FragmentViolationBinding
    private lateinit var buttonAdd: Button
    private lateinit var buttonSend: Button
    private lateinit var imageView: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var spinnerT: Spinner
    private lateinit var spinnerL: Spinner
    private var title: String = ""
    private lateinit var message: EditText
    private var location: String = ""
    private lateinit var responsible: EditText
    private lateinit var position: EditText
    private lateinit var author: String
    private lateinit var codename: String
    private var imageUrl: String = ""
    // Текущая дата и время:
    val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
    private var random: Int = 0

    val db = Firebase.firestore

    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }

    private val storage = FirebaseStorage.getInstance()
    private var storageRef = storage.reference
    lateinit var selectedImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        val username = intent.extras?.getString("name")
        Log.d("VIOLATION ACTIVITY", username.toString())

        random =(0..9999).random()
        // Название файла с указанием имени пользователя и общего идентификатора:
        val fileName = "${username}_${date}_${random}"

        binding = FragmentViolationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonAdd = binding.addPicture
        buttonSend = binding.send
        imageView = binding.picture

        spinnerT = findViewById(R.id.v_title)
        message = binding.vMessage
        spinnerL = findViewById(R.id.v_location)
        responsible = binding.vResponsible
        position = binding.vPosition
        author = username.toString()
        codename = fileName

        val itemsT = arrayOf("Не выбрано","Пропало электроснабжение", "Нарушение индивидуальной безопасности", "Нарушение пожарной безопасности", "Выход из строя техники", "Нарушение строительных конструкций", "Утечка химикатов")
        val adapterT = ArrayAdapter(this, R.layout.spinner, itemsT)
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerT.adapter = adapterT
        spinnerT.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                title = selectedItem
                val textView = view as? TextView
                if (textView != null) {
                    if (position == 0) {
                        // Если выбран первый элемент
                        textView.setTextColor(resources.getColor(R.color.dark_gray))
                    } else {
                        // Если выбран другой элемент
                        textView.setTextColor(resources.getColor(R.color.black))
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                title = "Неизвестно"
            }
        }

        val itemsL = arrayOf("Не выбрано", "Стапельный цех", "Цех сборки блоков", "АБК 450", "Серый дом", "Белый дом", "АБК БКП", "Сухой док")
        val adapterL = ArrayAdapter(this, R.layout.spinner, itemsL)
        adapterL.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerL.adapter = adapterL
        spinnerL.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                location = selectedItem
                val textView = view as? TextView
                if (textView != null) {
                    if (position == 0) {
                        // Если выбран первый элемент
                        textView.setTextColor(resources.getColor(R.color.dark_gray))
                    } else {
                        // Если выбран другой элемент
                        textView.setTextColor(resources.getColor(R.color.black))
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                location = "Неизвестно"
            }
        }

        bottomNavigationView = findViewById(R.id.menuViolations)

        bottomNavigationView.setSelectedItemId(R.id.navigation_violation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_appeals -> {
                    val intent = Intent(this, AppealsActivity::class.java)
                    intent.putExtra("name", username)
                    item.setChecked(true)
                    startActivity(intent)
                    true
                }
                R.id.navigation_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("name", username)
                    item.setChecked(true)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        imageView.visibility = View.GONE

        buttonAdd.setOnClickListener {
            pickImageGallery()
        }

        buttonSend.setOnClickListener {
            uploadImageToFirebase()
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onStart() {
        super.onStart()
        storageRef = FirebaseStorage.getInstance().reference
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageView.setImageURI((data?.data))
            selectedImageUri = data?.data ?: return
            showImage()
        }
    }

    private fun showImage() {
        imageView.visibility = View.VISIBLE // Показать ImageView
    }

    private fun uploadImageToFirebase() {
        val username = intent.extras?.getString("name")
        // Название файла с указанием имени пользователя и даты:
        val fileName = "${username}_${date}_${random}.jpg"

        val imageRef = storageRef.child("images/$fileName")
        val uploadTask = imageRef.putFile(selectedImageUri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Получение URL загруженного изображения
            val downloadUrlTask = taskSnapshot.storage.downloadUrl
            downloadUrlTask.addOnSuccessListener { downloadUri ->
                imageUrl = downloadUri.toString()
                Log.d("VIOLATION ACTIVITY IMAGE REF", imageUrl)
                textSend()
            }.addOnFailureListener { exception ->
                // Обработка ошибок при получении URL изображения
            }
        }.addOnFailureListener { exception ->
            // Обработка ошибок при загрузке изображения
        }
    }

    private fun textSend() {
        val sTitle = title.trim()
        val sMessage = message.text.toString().trim()
        val sLocation = location.trim()
        val sResponsible = responsible.text.toString().trim()
        val sPosition = position.text.toString().trim()
        val sAuthor = author.trim()
        val sDate = date
        val sCodename = codename.trim()

        val violation = hashMapOf(
            "title" to sTitle,
            "message" to sMessage,
            "location" to sLocation,
            "responsible" to sResponsible,
            "position" to sPosition,
            "author" to sAuthor,
            "date" to sDate,
            "codename" to sCodename,
            "image" to imageUrl,
            "status" to "Отправлено"
        )

        val documentName = sCodename

        db.collection("Violations")
            .document(documentName)
            .set(violation)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this, "Отправлено!", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Ошибка. $e", Toast.LENGTH_LONG).show()
            }
    }
}