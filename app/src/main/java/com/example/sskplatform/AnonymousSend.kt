@file:Suppress("DEPRECATION")

package com.example.sskplatform

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.sskplatform.databinding.AnonymousSendBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class AnonymousSend : AppCompatActivity() {
    private lateinit var binding: AnonymousSendBinding

    private lateinit var buttonAdd: Button
    private lateinit var buttonOut: Button
    private lateinit var buttonSend: Button
    private lateinit var imageView: ImageView
    private lateinit var title: EditText
    private lateinit var message: EditText
    private lateinit var location: EditText
    private lateinit var responsible: EditText
    private lateinit var position: EditText
    private lateinit var author: String
    private lateinit var codename: String
    private var imageUrl: String = ""
    val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date()) // Текущая дата и время
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

        val username = "Анонимно"

        random =(0..9999).random()
        val fileName = "${username}_${date}_${random}" // Название файла с указанием имени пользователя и общего идентификатора

        binding = AnonymousSendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonAdd = binding.addPictureAn
        buttonOut = binding.out
        buttonSend = binding.sendAn
        imageView = binding.pictureAn

        title = binding.vTitleAn
        message = binding.vMessageAn
        location = binding.vLocationAn
        responsible = binding.vResponsibleAn
        position = binding.vPositionAn
        author = username
        codename = fileName

        imageView.visibility = View.GONE

        buttonAdd.setOnClickListener {
            pickImageGallery()
        }

        buttonSend.setOnClickListener {
            uploadImageToFirebase()
        }

        buttonOut.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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

    private fun hideImage() {
        imageView.visibility = View.GONE // Скрыть ImageView
    }

    private fun uploadImageToFirebase() {
        val username = "Анонимно"
        val fileName = "${username}_${date}_${random}.jpg" // Название файла с указанием имени пользователя и даты

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
        val sTitle = title.text.toString().trim()
        val sMessage = message.text.toString().trim()
        val sLocation = location.text.toString().trim()
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