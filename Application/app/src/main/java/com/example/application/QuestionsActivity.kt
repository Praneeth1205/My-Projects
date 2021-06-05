package com.example.application

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.application.models.Constants
import com.example.application.models.Question
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mCorrectAnswers : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        mQuestionsList = Constants.getQuestions()

        getQuestions()
        updateQuestions()
        reverseTimer(60, countdown)

        optionA?.setOnClickListener(this)
        optionB?.setOnClickListener(this)
        optionC?.setOnClickListener(this)
        optionD?.setOnClickListener(this)
        submit_ans?.setOnClickListener(this)

    }


    private fun getQuestions() {
        val bundle = intent.extras
        val title = bundle?.getString("title")
        val imgUrl = bundle?.getString("url")

        quiz_name.text = title
        if (imgUrl!!.isEmpty()) {
            imageUrl.setImageResource(R.drawable.ipl)
        } else {
            Picasso.get().load(imgUrl).into(imageUrl)
        }

    }

    private fun updateQuestions() {
        val question = mQuestionsList!![mCurrentPosition - 1]
        question_count.text = "$mCurrentPosition" + "/" + mQuestionsList!!.size

        defaultOptionBack()
        if (mSelectedOptionPosition == mQuestionsList!!.size) {
            submit_ans.text = "FINISH"
        } else {
            submit_ans.text = "SUBMIT"
        }

        question_text.text = question.question
        optionA.text = question.option_A
        optionB.text = question.option_B
        optionC.text = question.option_C
        optionD.text = question.option_D
    }


    private fun defaultOptionBack() {
        val options = ArrayList<TextView>()
        options.add(0, optionA)
        options.add(1, optionB)
        options.add(2, optionC)
        options.add(3, optionD)

        for (option in options) {
            option.setTextColor(Color.parseColor("#000000"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.background_white_round
            )
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.optionA -> selectedOptionView(optionA, 1)
            R.id.optionB -> selectedOptionView(optionB, 2)
            R.id.optionC -> selectedOptionView(optionC, 3)
            R.id.optionD -> selectedOptionView(optionD, 4)
            R.id.submit_ans -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> updateQuestions()
                        else -> {
                            val intent = Intent(this,ResultActivity::class.java)
                            intent.putExtra("correct",mCorrectAnswers)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionsList!![mCurrentPosition - 1]
                    if (question.answer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_answer_background)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.answer, R.drawable.correct_answer_background)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        submit_ans.text = "FINISH"
                    } else {
                        submit_ans.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
           }

        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> optionA.background = ContextCompat.getDrawable(this, drawableView)
            2 -> optionB.background = ContextCompat.getDrawable(this, drawableView)
            3 -> optionC.background = ContextCompat.getDrawable(this, drawableView)
            4 -> optionD.background = ContextCompat.getDrawable(this, drawableView)
        }
    }

    private fun selectedOptionView(tv: TextView, selectedNum: Int) {
        defaultOptionBack()

        mSelectedOptionPosition = selectedNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_background_border
        )
    }

    private fun reverseTimer(Seconds: Int, tv: TextView) {
        object : CountDownTimer((Seconds * 1000 + 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds %= 60
                tv.text = (String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds))
            }

            override fun onFinish() {
                val intent = Intent(this@QuestionsActivity, ResultActivity::class.java)
                intent.putExtra("correct",mCorrectAnswers)
                startActivity(intent)
                finish()
            }
        }.start()
    }


}