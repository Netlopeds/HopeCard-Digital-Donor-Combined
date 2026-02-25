package com.example.hopecard.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.hopecard.R
import com.example.hopecard.ui.forgot.ForgotPasswordActivity
import com.example.hopecard.ui.profile.EditProfileActivity
import com.example.hopecard.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        // Containers for highlight (LinearLayouts)
        val emailContainer = findViewById<View>(R.id.emailContainer)
        val passwordContainer = findViewById<View>(R.id.passwordContainer)

        // CardViews (tap area) for focusing inputs
        val cardEmail = findViewById<View>(R.id.cardEmail)
        val cardPassword = findViewById<View>(R.id.cardPassword)

        // Login "button" (CardView + TextView)
        val cardLogin = findViewById<CardView>(R.id.cardLogin)
        val btnLogin = findViewById<TextView>(R.id.btnLogin)

        val tvForgot = findViewById<TextView>(R.id.tvForgot)
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)

        // Tap card focuses EditText (so highlight triggers even if user taps icon/blank area)
        cardEmail.setOnClickListener { etEmail.requestFocus() }
        cardPassword.setOnClickListener { etPassword.requestFocus() }

        // Highlight effect using bg_input_selector (state_selected)
        etEmail.setOnFocusChangeListener { _, hasFocus ->
            emailContainer.isSelected = hasFocus
        }
        etPassword.setOnFocusChangeListener { _, hasFocus ->
            passwordContainer.isSelected = hasFocus
        }

        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        tvForgot.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        val doLogin = {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString()

            when {
                email.isEmpty() -> {
                    etEmail.error = "Email is required"
                    etEmail.requestFocus()
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    etEmail.error = "Enter a valid email"
                    etEmail.requestFocus()
                }

                pass.isEmpty() -> {
                    etPassword.error = "Password is required"
                    etPassword.requestFocus()
                }

                pass.length < 6 -> {
                    etPassword.error = "Password must be at least 6 characters"
                    etPassword.requestFocus()
                }

                else -> {
                    // TODO(BACKEND): Authenticate against your backend (API call) and obtain a session/token.
                    // TODO(BACKEND): On success, persist token (EncryptedSharedPreferences) and fetch user profile.
                    Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_SHORT).show()

                    // Demo navigation target.
                    startActivity(Intent(this, EditProfileActivity::class.java))
                }
            }
        }

        cardLogin.setOnClickListener { doLogin() }
        btnLogin.setOnClickListener { doLogin() }
    }
}

