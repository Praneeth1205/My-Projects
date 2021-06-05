package com.example.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_earn_coin.*

class EarnCoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earn_coin)


        get_free_coins.setOnClickListener {
            val intent = Intent(this,FreeCoinsActivity::class.java)
            startActivity(intent)
        }
        back_btn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}