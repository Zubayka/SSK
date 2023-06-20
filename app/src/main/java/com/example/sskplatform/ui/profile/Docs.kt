package com.example.sskplatform.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.FileProvider
import com.example.sskplatform.R
import org.w3c.dom.Text
import java.io.File
import java.io.IOException

class Docs : AppCompatActivity() {

    private lateinit var KIB: TextView
    private lateinit var BRNKT: TextView
    private lateinit var IOTPVRNV: TextView
    private lateinit var IOTGS: TextView
    private lateinit var KANP: TextView
    private lateinit var OSOTPOPR: TextView
    private lateinit var POOT: TextView
    private lateinit var IPOTDE: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_docs)

        KIB = findViewById(R.id.KIB)
        BRNKT = findViewById(R.id.BRNKT)
        IOTPVRNV = findViewById(R.id.IOTPVRNV)
        IOTGS = findViewById(R.id.IOTGS)
        KANP = findViewById(R.id.KANP)
        OSOTPOPR = findViewById(R.id.OSOTPOPR)
        POOT = findViewById(R.id.POOT)
        IPOTDE = findViewById(R.id.IPOTDE)

        KIB.setOnClickListener{
            openKIB()
        }
        BRNKT.setOnClickListener{
            openBRNKT()
        }
        IOTPVRNV.setOnClickListener{
            openIOTPVRNV()
        }
        IOTGS.setOnClickListener{
            openIOTGS()
        }
        KANP.setOnClickListener{
            openKANP()
        }
        OSOTPOPR.setOnClickListener{
            openOSOTPOPR()
        }
        POOT.setOnClickListener{
            openPOOT()
        }
        IPOTDE.setOnClickListener{
            openIPOTDE()
        }
    }

    private fun openKIB() {
        val fileName = "Компьютер и безопасность.pdf"

        try {
            val file = File(cacheDir, fileName)

            // Если файл не существует в кэше, копируем его из папки assets
            if (!file.exists()) {
                val inputStream = assets.open(fileName)
                val outputStream = file.outputStream()

                inputStream.copyTo(outputStream)

                inputStream.close()
                outputStream.close()
            }

            val uri = FileProvider.getUriForFile(this,
                "com.example.sskplatform.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun openBRNKT() {
        val fileName = "Безопасность работы на копировальной технике.pdf"

        try {
            val file = File(cacheDir, fileName)

            // Если файл не существует в кэше, копируем его из папки assets
            if (!file.exists()) {
                val inputStream = assets.open(fileName)
                val outputStream = file.outputStream()

                inputStream.copyTo(outputStream)

                inputStream.close()
                outputStream.close()
            }

            val uri = FileProvider.getUriForFile(this, "com.example.sskplatform.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun openIOTPVRNV() {
        val fileName = "ИОТ при выполнении работ на высоте.pdf"

        try {
            val file = File(cacheDir, fileName)

            // Если файл не существует в кэше, копируем его из папки assets
            if (!file.exists()) {
                val inputStream = assets.open(fileName)
                val outputStream = file.outputStream()

                inputStream.copyTo(outputStream)

                inputStream.close()
                outputStream.close()
            }

            val uri = FileProvider.getUriForFile(this, "com.example.sskplatform.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun openIOTGS() {
        val fileName = "ИОТ электрогазосварщик.pdf"

        try {
            val file = File(cacheDir, fileName)

            // Если файл не существует в кэше, копируем его из папки assets
            if (!file.exists()) {
                val inputStream = assets.open(fileName)
                val outputStream = file.outputStream()

                inputStream.copyTo(outputStream)

                inputStream.close()
                outputStream.close()
            }

            val uri = FileProvider.getUriForFile(this, "com.example.sskplatform.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun openKANP() {
        val fileName = "Комплектация аптечки на предприятии.pdf"

        try {
            val file = File(cacheDir, fileName)

            // Если файл не существует в кэше, копируем его из папки assets
            if (!file.exists()) {
                val inputStream = assets.open(fileName)
                val outputStream = file.outputStream()

                inputStream.copyTo(outputStream)

                inputStream.close()
                outputStream.close()
            }

            val uri = FileProvider.getUriForFile(this, "com.example.sskplatform.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun openOSOTPOPR() {
        val fileName = "Обязанности СОТ по ОПР 2907.pdf"

        try {
            val file = File(cacheDir, fileName)

            // Если файл не существует в кэше, копируем его из папки assets
            if (!file.exists()) {
                val inputStream = assets.open(fileName)
                val outputStream = file.outputStream()

                inputStream.copyTo(outputStream)

                inputStream.close()
                outputStream.close()
            }

            val uri = FileProvider.getUriForFile(this, "com.example.sskplatform.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun openPOOT() {
        val fileName = "Памятка организация охраны труда.pdf"

        try {
            val file = File(cacheDir, fileName)

            // Если файл не существует в кэше, копируем его из папки assets
            if (!file.exists()) {
                val inputStream = assets.open(fileName)
                val outputStream = file.outputStream()

                inputStream.copyTo(outputStream)

                inputStream.close()
                outputStream.close()
            }

            val uri = FileProvider.getUriForFile(this, "com.example.sskplatform.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun openIPOTDE() {
        val fileName = "Инструкция по охране труда для экономиста.pdf"

        try {
            val file = File(cacheDir, fileName)

            // Если файл не существует в кэше, копируем его из папки assets
            if (!file.exists()) {
                val inputStream = assets.open(fileName)
                val outputStream = file.outputStream()

                inputStream.copyTo(outputStream)

                inputStream.close()
                outputStream.close()
            }

            val uri = FileProvider.getUriForFile(this, "com.example.sskplatform.fileprovider", file)
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "application/pdf")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}