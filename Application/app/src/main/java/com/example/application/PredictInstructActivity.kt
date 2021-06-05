package com.example.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.application.fragments.PredictRulesFragment
import com.example.application.models.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_instruction.*
import kotlinx.android.synthetic.main.activity_instruction.start_quiz_btn
import kotlinx.android.synthetic.main.activity_predict_instruct.*
import kotlinx.android.synthetic.main.layout_list_instructs.*

class PredictInstructActivity : AppCompatActivity() {

    private var instructionList: List<Data> = ArrayList<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predict_instruct)

        val backBtn: ImageView = findViewById(R.id.back_btn)

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        instructionList = ArrayList()

        getInstruction()

        view_predict_rules.setOnClickListener {
            val dialog = PredictRulesFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }

    }

    private fun getInstruction() {
        val bundle = intent.extras
       // val quizTitle: String? = bundle?.getString("quizTitle")
        val prize: String? = bundle?.getString("prize")
        val entry: String? = bundle?.getString("entry")
        val oneP: String? = bundle?.getString("one")
        val twoP: String? = bundle?.getString("two")
        val imgUrl = bundle?.getString("urlOne")
        val imgUrlTwo = bundle?.getString("urlTwo")

        prize_coins.text = "Prize $prize coins"
        entry_fee.text = "Entry: $entry coins"
        p_one.text = oneP
        p_two.text = twoP

        if (imgUrl!!.isEmpty()) {
            image_first_participant.setImageResource(R.drawable.ipl)
        } else {
            Picasso.get().load(imgUrl).into(image_first_participant)
        }

        if (imgUrlTwo!!.isEmpty()) {
            image_second_participant.setImageResource(R.drawable.ipl)
        } else {
            Picasso.get().load(imgUrlTwo).into(image_second_participant)
        }

        start_predict_btn.setOnClickListener {
            val intent = Intent(this,PredictQuestionsActivity::class.java)
            intent.putExtra("title1",p_one.text)
            intent.putExtra("title2",p_two.text)
            intent.putExtra("url1",imgUrl)
            intent.putExtra("url2",imgUrlTwo)
            startActivity(intent)
            finish()
        }


    }
}