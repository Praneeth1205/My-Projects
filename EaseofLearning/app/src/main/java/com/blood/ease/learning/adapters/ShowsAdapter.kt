package com.blood.ease.learning.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blood.ease.learning.R
import com.blood.ease.learning.ShowDetailsActivity
import com.blood.ease.learning.model.Shows
import com.squareup.picasso.Picasso

class ShowsAdapter(private val mContext : Context, private val shows : List<Shows>) : RecyclerView.Adapter<ShowsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.video_list,parent,false))
    }

    override fun onBindViewHolder(holder: ShowsAdapter.ViewHolder, position: Int) {
        val show = shows[position]
        holder.showname.text = show.getShowName()
        Picasso.get().load(show.getImageUrl()).into(holder.imageUrl)

        holder.itemView.setOnClickListener {
            val bundle  = Bundle()
            val intent =  Intent(mContext, ShowDetailsActivity::class.java)
            intent.putExtra("showname",holder.showname.text.toString())
            intent.putExtra("url",holder.imageUrl.toString())
            mContext.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return shows.size
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