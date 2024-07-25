package com.example.mindsage_project


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Find views by ID
        val editTextEmail = findViewById<EditText>(R.id.email)
        val editTextPassword = findViewById<EditText>(R.id.password)
        val buttonLogin = findViewById<Button>(R.id.loginButton)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val buttonGoogle = findViewById<ImageButton>(R.id.GoogleButton)
        val buttonFacebook = findViewById<ImageButton>(R.id.FbButton)
        val buttonApple = findViewById<ImageButton>(R.id.AppleButton)


        // Set click listener for the back button
        backButton.setOnClickListener {
            // Navigate to FirstPage activity if no user is logged in
            if (auth.currentUser == null) {
                val intent = Intent(this, FirstPage::class.java)
                startActivity(intent)
                finish()
            } else {
                // Otherwise, just finish the activity
                finish()
            }
        }

        // Set click listener for the login button
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Validate com.example.mindsage_project.getInput
            if (validateInput(email, password)) {
                // Handle login logic here
                loginUser(email, password)
            }
        }
        // Check if user is already logged in
        if (auth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    // Function to validate com.example.mindsage_project.getInput
    private fun validateInput(email: String, password: String): Boolean {
        // Check if email and password are not empty
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            return false
        }
        // Check if password is not empty
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            return false
        }
        // Check if email is a valid email address
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
    // Function to handle login logic using user's email and password
    private fun loginUser(email: String, password: String) {

        // Show a progress dialog while logging in
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Logging in...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        // Sign in with Firebase
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progressDialog.dismiss() // Dismiss the progress dialog
                // Check if sign in was successful
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(this, "Authentication successful.", Toast.LENGTH_SHORT).show()
                    // Navigate to your main activity
                    val intent = Intent(this, ChatActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this,
                        "Authentication failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

    }

}
