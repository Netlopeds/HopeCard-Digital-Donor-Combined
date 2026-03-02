package com.example.hopecard.ui.forgot

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.hopecard.R
import com.example.hopecard.ui.signup.SignUpActivity

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val images = listOf(R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4)

        setContent {
            ResetPasswordComposeScreen(
                carouselImages = images,
                onContinue = { newPassword ->
                    // Matches old behavior
                    Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_LONG).show()
                    finish()
                },
                onSignUp = {
                    startActivity(Intent(this, SignUpActivity::class.java))
                }
            )
        }
    }
}
