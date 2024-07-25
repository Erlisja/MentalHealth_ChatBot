package com.example.mindsage_project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_screen)

        // Simulate a loading delay
        Handler().postDelayed({
            val intent = Intent(this, FirstPage::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 2 seconds delay
    }
}
