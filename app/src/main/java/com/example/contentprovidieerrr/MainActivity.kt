package com.example.contentprovidieerrr

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var text : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.text)
        text.setOnClickListener(){
            val intentDestination = Intent (this@MainActivity, Halamansms::class.java)
            startActivity(intentDestination )
        }
        }
    }
