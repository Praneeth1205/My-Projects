package com.loaner.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.activity_verify.*

class VerifyActivity : AppCompatActivity() {
    lateinit var prefs: Prefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)

        prefs = Prefs(this)

        go_btn?.setOnClickListener {
            if (pin_entry.text.toString() == ""){
                Toast.makeText(this,"Enter OTP...",Toast.LENGTH_SHORT).show()
            }else{
                prefs.isLoggined = true
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}