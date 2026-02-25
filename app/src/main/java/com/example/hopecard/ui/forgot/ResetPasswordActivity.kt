package com.example.hopecard.ui.forgot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.hopecard.R
import com.example.hopecard.ui.signup.CarouselAdapter

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private val sliderHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        // Carousel setup
        viewPager = findViewById(R.id.viewPagerCarousel)
        val images = listOf(R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4)
        viewPager.adapter = CarouselAdapter(images)
        val sliderRunnable = object : Runnable {
            override fun run() {
                viewPager.currentItem = (viewPager.currentItem + 1) % images.size
                sliderHandler.postDelayed(this, 3000)
            }
        }
        sliderHandler.postDelayed(sliderRunnable, 3000)

        val btnContinue = findViewById<TextView>(R.id.btnContinue)
        val passwordInput = findViewById<EditText>(R.id.newPasswordInput)

        btnContinue.setOnClickListener {
            if (passwordInput.text.isNotEmpty()) {
                // TODO(BACKEND): Submit new password to backend (requires verified OTP token).
                Toast.makeText(this, "Password Reset Successfully! (stub)", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sliderHandler.removeCallbacksAndMessages(null)
    }
}
