package com.example.application.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.application.R
import com.example.application.models.Winner
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class WinnersAdapter(private val mContext : Context,
                     private val winnerList: List<Winner>):RecyclerView.Adapter<WinnersAdapter.ViewHolder>() {
    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
         var imageUrlOne : CircleImageView = itemView.findViewById(R.id.imageUrla)
         var imageUrlTwo : CircleImageView = itemView.findViewById(R.id.imageUrlb)
         var imageUrlThree : CircleImageView = itemView.findViewById(R.id.imageUrlc)
         var quizName : TextView = itemView.findViewById(R.id.quiz_name_winners)
         var winningCoins : TextView = itemView.findViewById(R.id.coins_winners)
         var winningCount : TextView = itemView.findViewById(R.id.winner_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_winner_list,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val win = winnerList[position]
       holder.quizName.text = win.getQuizName()
        holder.winningCoins.text = win.getWinningCoins()
        holder.winningCount.text = win.getWinningCount()

        Picasso.get().load(win.getImageUrlOne()).into(holder.imageUrlOne)
        Picasso.get().load(win.getImageUrlOne()).into(holder.imageUrlTwo)
        Picasso.get().load(win.getImageUrlOne()).into(holder.imageUrlThree)
    }

    override fun getItemCount(): Int {
       return winnerList.size
    }
}