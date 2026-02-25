package com.example.hopecard.ui.forgot

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.hopecard.R
import com.example.hopecard.ui.signup.CarouselAdapter
import com.example.hopecard.ui.signup.SignUpActivity

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private val sliderHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

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
        val emailInput = findViewById<EditText>(R.id.emailInput)

        // Dynamically switch inputType: phone keyboard if starts with a digit, email keyboard otherwise
        emailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s?.toString() ?: return
                val cursorPos = emailInput.selectionEnd
                if (text.isNotEmpty() && text[0].isDigit()) {
                    if (emailInput.inputType != InputType.TYPE_CLASS_PHONE) {
                        emailInput.inputType = InputType.TYPE_CLASS_PHONE
                        emailInput.setSelection(cursorPos.coerceAtMost(emailInput.text.length))
                    }
                } else {
                    val emailType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    if (emailInput.inputType != emailType) {
                        emailInput.inputType = emailType
                        emailInput.setSelection(cursorPos.coerceAtMost(emailInput.text.length))
                    }
                }
            }
        })

        btnContinue.setOnClickListener {
            val input = emailInput.text.toString().trim()
            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter your email or phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val isPhone = input.matches(Regex("^[+]?[0-9\\s\\-]{7,15}$"))
            val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
            if (!isPhone && !isEmail) {
                Toast.makeText(this, "Please enter a valid email address or phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // TODO(BACKEND): Trigger OTP request endpoint (email/phone -> send OTP).
            startActivity(Intent(this, OtpVerifyActivity::class.java))
        }

        // Sign up here button
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)
        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sliderHandler.removeCallbacksAndMessages(null)
    }
}
