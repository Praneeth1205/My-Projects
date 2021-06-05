package com.example.application.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.application.PredictInstructActivity
import com.example.application.R
import com.example.application.models.Data
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PredictAdapter(private val mContext : Context,
                     private val predictList: List<Data>):RecyclerView.Adapter<PredictAdapter.ViewHolder>() {
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val imageUrl: CircleImageView = itemView.findViewById(R.id.image_predict_one)
        val imageUrlTwo: CircleImageView = itemView.findViewById(R.id.image_predict_two)
        val quizTitle: TextView = itemView.findViewById(R.id.name_predict)
        val prizeCoins: TextView = itemView.findViewById(R.id.winning_coins_predict)
        val userCount: TextView = itemView.findViewById(R.id.noOf_users_predict)
        val entryFee: TextView = itemView.findViewById(R.id.entry_fee_predict)
        val participantOne : TextView = itemView.findViewById(R.id.participant_one_name)
        val participantTwo : TextView = itemView.findViewById(R.id.participant_two_name)
        val joinNowBtn : Button = itemView.findViewById(R.id.join_now)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_predict_list,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val predict = predictList[position]


        holder.quizTitle.text = predict.getQuizTitle()
        holder.entryFee.text = predict.getEntryFee()
        holder.userCount.text = predict.getUserCount()
        holder.prizeCoins.text = predict.getPrizeCoins()
        holder.participantOne.text = predict.getParticipantOne()
        holder.participantTwo.text = predict.getParticipantTwo()

        if (predict.getImageUrl().isEmpty()) {
            holder.imageUrl.setImageResource(R.drawable.ipl)
        } else {
            Picasso.get().load(predict.getImageUrl()).into(holder.imageUrl)
        }
        if (predict.getImageUrlTwo().isEmpty()) {
            holder.imageUrlTwo.setImageResource(R.drawable.ipl)
        } else {
            Picasso.get().load(predict.getImageUrlTwo()).into(holder.imageUrlTwo)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext,PredictInstructActivity::class.java)
            //intent.putExtra("quizTitle",holder.quizTitle.text.toString())
            intent.putExtra("prize",holder.prizeCoins.text.toString())
            intent.putExtra("entry",holder.entryFee.text.toString())
            intent.putExtra("one",holder.participantOne.text.toString())
            intent.putExtra("two",holder.participantTwo.text.toString())
            intent.putExtra("urlOne",predict.getImageUrl())
            intent.putExtra("urlTwo",predict.getImageUrlTwo())
            mContext.startActivity(intent)
        }

        holder.joinNowBtn.setOnClickListener {
            val intent = Intent(mContext,PredictInstructActivity::class.java)
            //intent.putExtra("quizTitle",holder.quizTitle.text.toString())
            intent.putExtra("prize",holder.prizeCoins.text.toString())
            intent.putExtra("entry",holder.entryFee.text.toString())
            intent.putExtra("one",holder.participantOne.text.toString())
            intent.putExtra("two",holder.participantTwo.text.toString())
            intent.putExtra("urlOne",predict.getImageUrl())
            intent.putExtra("urlTwo",predict.getImageUrlTwo())
            mContext.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return predictList.size
    }
}