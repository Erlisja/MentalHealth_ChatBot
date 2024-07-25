package com.example.mindsage_project

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SideBarActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sidebar)


        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()


        // Find the LinearLayout that wraps the chat icon and text
        val chatLayout: LinearLayout = findViewById(R.id.menu_chat_layout)
        // Find the LinearLayout that wraps the settings icon and text
        val settingsLayout: LinearLayout = findViewById(R.id.menu_settings_layout)
        val logoutLayout: LinearLayout = findViewById(R.id.menu_logout_layout)


        chatLayout.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        settingsLayout.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        // Logout button
        logoutLayout.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        auth.signOut()

        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()


    }
}


