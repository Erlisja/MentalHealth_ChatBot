package com.example.mindsage_project

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val backButton = findViewById<ImageButton>(R.id.backButton)
        val userAccountButton = findViewById<Button>(R.id.userAccountButton)


        backButton.setOnClickListener {
            finish()
        }

    }
}