package com.blood.ease.learning.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blood.ease.learning.R
import com.blood.ease.learning.model.Question

class QuestionAdapter(private val mContext:Context,
                      private val qlist:ArrayList<Question>): RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.question_list_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ques = qlist[position]

        holder.question.text = ques.getQuestion()
        holder.option1.text = ques.getOption1()
        holder.option2.text = ques.getOption2()
        holder.option3.text = ques.getOption3()
        holder.option4.text = ques.getOption4()
        holder.answer.text = ques.getAnswer()
    }

    override fun getItemCount(): Int {
        return qlist.size
    }

    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        val question : TextView = itemView.findViewById(R.id.question)
        val option1 : TextView = itemView.findViewById(R.id.option1)
        val option2 : TextView = itemView.findViewById(R.id.option2)
        val option3 : TextView = itemView.findViewById(R.id.option3)
        val option4 : TextView = itemView.findViewById(R.id.option4)
        val answer : TextView = itemView.findViewById(R.id.answer)
    }

}