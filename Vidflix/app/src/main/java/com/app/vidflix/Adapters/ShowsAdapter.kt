package com.app.vidflix.Adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.vidflix.Models.Shows
import com.app.vidflix.R
import com.app.vidflix.ShowDetailsActivity
import com.squareup.picasso.Picasso

/**
 * Created by Prakash Reddy on 1/16/2021.
 */
class ShowsAdapter(private val mContext : Context, private val shows : List<Shows>) : RecyclerView.Adapter<ShowsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.shows_list,parent,false))
    }

    override fun getItemCount(): Int {
        return shows.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val show = shows[position]
        holder.showname.text = show.getShowName()
        Picasso.get().load(show.getImageUrl()).into(holder.imageUrl)

        holder.itemView.setOnClickListener {
            val bundle  = Bundle()
            val intent =  Intent(mContext,ShowDetailsActivity::class.java)
            intent.putExtra("showname",holder.showname.text.toString())
            mContext.startActivity(intent)
        }


    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageUrl = itemView.findViewById<ImageView>(R.id.image_show)
        val showname = itemView.findViewById<TextView>(R.id.name_show)

       // fun bind(show : Shows){
         //   showname.text = show.getShowName()
           // Picasso.get().load(show.getImageUrl()).into(imageUrl)
        //}
    }
}