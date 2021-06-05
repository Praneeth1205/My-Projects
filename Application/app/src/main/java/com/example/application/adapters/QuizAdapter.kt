package com.example.application.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.application.InstructionActivity
import com.example.application.R
import com.example.application.models.Data
import com.squareup.picasso.Picasso

class QuizAdapter(
    private val mContext: Context,
    private val quizList: List<Data>
) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageUrl: ImageView = itemView.findViewById(R.id.image_quiz)
        val quizTitle: TextView = itemView.findViewById(R.id.name_quiz)
        val prizeCoins: TextView = itemView.findViewById(R.id.winning_coins)
        val userCount: TextView = itemView.findViewById(R.id.noOf_users)
        val entryFee: TextView = itemView.findViewById(R.id.entry_fee)
        val playBtn : TextView = itemView.findViewById(R.id.play_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.layout_quiz_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return quizList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quiz = quizList[position]

        holder.quizTitle.text = quiz.getQuizTitle()
        holder.entryFee.text = quiz.getEntryFee()
        holder.userCount.text = quiz.getUserCount()
        holder.prizeCoins.text = quiz.getPrizeCoins()

        if (quiz.getImageUrl().isEmpty()) {
            holder.imageUrl.setImageResource(R.drawable.ipl)
        } else {
            Picasso.get().load(quiz.getImageUrl()).into(holder.imageUrl)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext,InstructionActivity::class.java)
            intent.putExtra("quizTitle",holder.quizTitle.text.toString())
            intent.putExtra("url",quiz.getImageUrl())
            intent.putExtra("prize",holder.prizeCoins.text.toString())
            intent.putExtra("entry",holder.entryFee.text.toString())
            mContext.startActivity(intent)
        }
        holder.playBtn.setOnClickListener {
            val intent = Intent(mContext,InstructionActivity::class.java)
            intent.putExtra("quizTitle",holder.quizTitle.text.toString())
            intent.putExtra("url",quiz.getImageUrl())
            intent.putExtra("prize",holder.prizeCoins.text.toString())
            intent.putExtra("entry",holder.entryFee.text.toString())
            mContext.startActivity(intent)
        }
    }

}