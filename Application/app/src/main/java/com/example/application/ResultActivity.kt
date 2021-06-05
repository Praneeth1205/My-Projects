package com.example.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.application.fragments.RankBreakupFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_questions.*
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        score.text = intent.getIntExtra("correct",0).toString()
        name_quiz.text = intent.getStringExtra("title")

        back_btn_result?.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        view_rank_breakup?.setOnClickListener {
            val dialog = RankBreakupFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }

    }

}