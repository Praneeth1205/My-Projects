package com.loaner.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.activity_education_detail.*


class EducationDetailActivity : AppCompatActivity() {

    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education_detail)

        prefs = Prefs(this)

        btn_next?.setOnClickListener {
            val intent = Intent(this,BankDetailActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}