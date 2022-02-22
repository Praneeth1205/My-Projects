package com.blood.ease.learning.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blood.ease.learning.R
import com.blood.ease.learning.VideoPlayerActivity
import com.blood.ease.learning.model.Source
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso

class VideoAdapter(private val mContext : Context,
                   private val videos: List<Source>): RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private var firebaseUser: FirebaseUser? = null

    class ViewHolder(itemVew: View) : RecyclerView.ViewHolder(itemVew) {


        val descriptionVid: TextView = itemVew.findViewById(R.id.description_video)
        val directorVid: TextView = itemVew.findViewById(R.id.director_video)
        val titleVid: TextView = itemVew.findViewById(R.id.title_video)
        val vidImageSrc: ImageView = itemVew.findViewById(R.id.image_video)
        val playbtn: Button = itemVew.findViewById(R.id.play_btn)
        //val wishlistbtn: Button = itemVew.findViewById(R.id.wishlist_btn)
        // val downloadbtn: ImageView = itemVew.findViewById(R.id.btn_download)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.details_file,
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: VideoAdapter.ViewHolder, position: Int) {
        firebaseUser = FirebaseAuth.getInstance().currentUser

        val video = videos[position]
        //Picasso.get()?.load(video.getCastImageUrl())?.into(holder.castImageSrc)
        holder.descriptionVid.text = video.getDescription()
        holder.directorVid.text = video.getAuthor()
       // holder.genreVid.text = video.getGenre()
       // holder.mainCastVid.text = video.getMainCast()
       // holder.releaseVid.text = video.getRelease()
        holder.titleVid.text = video.getTitle()

        Picasso.get().load(video.getImageUrl()).into(holder.vidImageSrc)

        holder.playbtn.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(mContext, VideoPlayerActivity::class.java)
            intent.putExtra("title", holder.titleVid.text.toString())
            mContext.startActivity(intent)
        }

    }

}