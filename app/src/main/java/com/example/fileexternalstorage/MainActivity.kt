package com.example.fileexternalstorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.os.Environment
import android.util.Log
import java.io.*

class MainActivity : AppCompatActivity() {
    private val filepath = "MyFileStorage"
    internal var myExternalFile: File?=null
    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            true
        } else {
            false
        }
    }
    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            true
        } else{
            false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fileName = findViewById(R.id.editTextFile) as EditText
        val fileData = findViewById(R.id.editTextData) as EditText
        val saveButton = findViewById<Button>(R.id.button_save) as Button

        saveButton.setOnClickListener(View.OnClickListener {
            Log.d("insideTest", "setOnClickListener start")
            myExternalFile = File(getExternalFilesDir(filepath), fileName.text.toString())
            try {
                Log.d("insideTest", "setOnClickListener try")
                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(fileData.text.toString().toByteArray())
                Log.d("insideTest", fileData.text.toString())

                fileOutPutStream.close()
            } catch (e: IOException) {
                Log.d("insideTest", "setOnClickListener catch")

                e.printStackTrace()
            }

            Log.d("insideTest", "setOnClickListener before toast")
            Toast.makeText(this,"data save",Toast.LENGTH_SHORT).show()
        })


        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            saveButton.isEnabled = false
            Log.d("insideTest", "InsideStorage")
        }
    }
}