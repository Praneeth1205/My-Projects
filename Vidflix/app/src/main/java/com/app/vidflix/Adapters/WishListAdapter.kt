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
import com.app.vidflix.Models.Videos
import com.app.vidflix.R
import com.app.vidflix.ShowDetailsActivity
import com.squareup.picasso.Picasso

/**
 * Created by Prakash Reddy on 1/23/2021.
 */
class WishListAdapter(private val mContext : Context,
                      private val wishList : List<Videos>) : RecyclerView.Adapter<WishListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.wish_list_shows,parent,false))
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = wishList[position]

        holder.showname.text = video.getTitle()
        Picasso.get().load(video.getVidImageUrl()).into(holder.showImage)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(mContext,ShowDetailsActivity::class.java)
            intent.putExtra("showname",holder.showname.text.toString())
            mContext.startActivity(intent)
        }

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val showImage : ImageView = itemView.findViewById(R.id.show_image)
        val showname : TextView = itemView.findViewById(R.id.show_title)

    }
}