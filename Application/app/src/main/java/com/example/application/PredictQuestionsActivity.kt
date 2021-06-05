package com.example.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_predict_instruct.*
import kotlinx.android.synthetic.main.activity_predict_questions.*

class PredictQuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_predict_questions)


        getQuestion()
    }

    private fun getQuestion() {
        val bundle = intent.extras
        val imgUrlone = bundle?.getString("url1")
        val imgUrltwo = bundle?.getString("url2")
        val titleOne = bundle?.getString("title1")
        val titleTwo = bundle?.getString("title2")

        oneName.text = titleOne
        twoName.text = titleTwo

        if (imgUrlone!!.isEmpty()) {
            image_first_participant.setImageResource(R.drawable.ipl)
        } else {
            Picasso.get().load(imgUrlone).into(oneParticipant)
        }

        if (imgUrltwo!!.isEmpty()) {
            image_first_participant.setImageResource(R.drawable.ipl)
        } else {
            Picasso.get().load(imgUrltwo).into(twoParticipant)
        }

    }
}