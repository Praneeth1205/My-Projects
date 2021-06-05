package com.example.application

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.application.fragments.QuizRulesFragment
import com.example.application.fragments.RankBreakupFragment
import com.example.application.models.Data
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_instruction.*
import kotlinx.android.synthetic.main.layout_list_instructs.*

class InstructionActivity : AppCompatActivity() {

    private var instructionList: List<Data> = ArrayList<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruction)

        val backBtn: ImageView = findViewById(R.id.back_btn)

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        instructionList = ArrayList()

        getInstruction()

        view_rules?.setOnClickListener {
            val dialog = QuizRulesFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }

        view_ranking_breakup?.setOnClickListener {
            val dialog = RankBreakupFragment()

            dialog.show(supportFragmentManager,"customDialog")
        }

    }

    private fun getInstruction() {

        val bundle = intent.extras
        val quizTitle: String? = bundle?.getString("quizTitle")
        val prize: String? = bundle?.getString("prize")
        val entry: String? = bundle?.getString("entry")
        val imgUrl = bundle?.getString("url")

        name_quiz_instruct.text = quizTitle
        winning_coins_instruct.text = prize
        entry_fee_instruct.text = "Entry Fee: $entry "
        if (imgUrl!!.isEmpty()) {
            image_quiz_instruct.setImageResource(R.drawable.ipl)
        } else {
            Picasso.get().load(imgUrl).into(image_quiz_instruct)
        }

        start_quiz_btn.setOnClickListener {
            val intent = Intent(this,QuestionsActivity::class.java)
            intent.putExtra("title",name_quiz_instruct.text.toString())
            intent.putExtra("url",imgUrl)
            startActivity(intent)
            finish()
        }


    }
}






















