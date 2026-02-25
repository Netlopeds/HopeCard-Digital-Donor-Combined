package com.example.hopecard.ui.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hopecard.R
import java.util.Calendar

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etDob = findViewById<EditText>(R.id.etDob)
        etDob.setOnClickListener { showDatePicker(etDob) }

        findViewById<android.view.View>(R.id.btnUpdate).setOnClickListener {
            // TODO(BACKEND): Send updated profile fields to backend.
            Toast.makeText(this, "Profile Updated Successfully (stub)", Toast.LENGTH_SHORT).show()
        }

        findViewById<android.view.View>(R.id.btnChangeProfilePicture).setOnClickListener {
            // TODO(BACKEND): Upload profile image (image picker + upload endpoint).
            Toast.makeText(this, "Change Profile Picture Clicked (stub)", Toast.LENGTH_SHORT).show()
        }

        // TODO(BACKEND): Load existing profile data and populate fields on screen load.
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "${selectedMonth + 1}/$selectedDay/$selectedYear"
                editText.setText(date)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
}

