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
import com.app.vidflix.Models.Videos
import com.app.vidflix.R
import com.app.vidflix.ShowDetailsActivity
import com.squareup.picasso.Picasso


class SearchAdapter(private val mContext : Context,
                    private val videosList : List<Shows>) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.search_videos,parent,false))
    }

    override fun getItemCount(): Int {
       return videosList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val video = videosList[position]

        holder.showname.text = video.getShowName()
        Picasso.get().load(video.getImageUrl()).into(holder.showImage)

        holder.itemView.setOnClickListener {
            val intent =  Intent(mContext,ShowDetailsActivity::class.java)
            intent.putExtra("showname",holder.showname.text.toString())
            mContext.startActivity(intent)

        }

    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val showname : TextView = itemView.findViewById(R.id.search_title)
        val showImage : ImageView = itemView.findViewById(R.id.search_video)


    }
}