package com.example.mindsage_project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class FirstPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            // Handle login logic here
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.signupButton).setOnClickListener {
            // Handle signup logic here
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}