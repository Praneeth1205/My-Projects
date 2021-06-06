package com.loaner.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signin_btn?.setOnClickListener {
            if (phone_number.text.toString() == "") {
                makeText(
                    this, "Please enter your number",
                    LENGTH_SHORT
                ).show()
            } else if (!checkbox_tc.isChecked) {
                makeText(
                    this, "Please accept our terms&conditions",
                    LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, VerifyActivity::class.java)
                startActivity(intent)
            }
        }

        terms_conditions?.setOnClickListener {
            startActivity(Intent(this,TermsConditionsActivity::class.java))
        }

        privacy_policy?.setOnClickListener {
            startActivity(Intent(this,PrivacypolicyActivity::class.java))
        }

    }
}