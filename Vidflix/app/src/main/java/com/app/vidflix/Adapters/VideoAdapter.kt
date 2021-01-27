package com.app.vidflix.Adapters

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.vidflix.Models.Videos
import com.app.vidflix.R
import com.app.vidflix.VideoPlayerActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import org.xml.sax.Parser

/**
 * Created by Prakash Reddy on 1/21/2021.
 */
class VideoAdapter(private val mContext : Context,
                   private val videos: List<Videos>): RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private var firebaseUser: FirebaseUser? = null

    class ViewHolder(itemVew: View) : RecyclerView.ViewHolder(itemVew) {

        val castImageSrc: ImageView = itemVew.findViewById(R.id.image_maincast)
        val descriptionVid: TextView = itemVew.findViewById(R.id.description_video)
        val directorVid: TextView = itemVew.findViewById(R.id.director_video)
        val genreVid: TextView = itemVew.findViewById(R.id.genre_video)
        val mainCastVid: TextView = itemVew.findViewById(R.id.cast_name_video)
        val releaseVid: TextView = itemVew.findViewById(R.id.release_video)
        val titleVid: TextView = itemVew.findViewById(R.id.title_video)
        val vidImageSrc: ImageView = itemVew.findViewById(R.id.image_video)
        val playbtn: Button = itemVew.findViewById(R.id.play_btn)
        val wishlistbtn: Button = itemVew.findViewById(R.id.wishlist_btn)
       // val downloadbtn: ImageView = itemVew.findViewById(R.id.btn_download)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.list_videos_details,
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
        Picasso.get()?.load(video.getCastImageUrl())?.into(holder.castImageSrc)
        holder.descriptionVid.text = video.getDescription()
        holder.directorVid.text = video.getDirector()
        holder.genreVid.text = video.getGenre()
        holder.mainCastVid.text = video.getMainCast()
        holder.releaseVid.text = video.getRelease()
        holder.titleVid.text = video.getTitle()

        Picasso.get()?.load(video.getVidImageUrl())?.into(holder.vidImageSrc)

        holder.playbtn.setOnClickListener {
            val bundle = Bundle()
            val intent = Intent(mContext, VideoPlayerActivity::class.java)
            intent.putExtra("title", holder.titleVid.text.toString())
            mContext.startActivity(intent)
        }

        checkAddedToWishlsit(video.getTitle(), holder.wishlistbtn)

        holder.wishlistbtn.setOnClickListener {
            if (holder.wishlistbtn.tag == "Add") {
                val ref = FirebaseDatabase.getInstance().reference
                    .child("Wishlist")
                    .child(firebaseUser!!.uid)


                val userMap = HashMap<String, Any>()

                userMap["title"] = video.getTitle()
                userMap["vidImageUrl"] = video.getVidImageUrl()

                ref.child(video.getTitle()).setValue(userMap)

            } else {
                FirebaseDatabase.getInstance().reference
                    .child("Wishlist")
                    .child(firebaseUser!!.uid)
                    .child(video.getTitle())
                    .removeValue()
            }
        }

    }


    private fun checkAddedToWishlsit(title: String, wishlistbtn: Button) {
        val addRef = FirebaseDatabase.getInstance().reference
            .child("Wishlist")
            .child(firebaseUser!!.uid)

        addRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(title).exists()) {
                    wishlistbtn.setText("Remove from Wishlist")
                    wishlistbtn.tag = "Added"
                } else {
                    wishlistbtn.setText("Add to Wishlist")
                    wishlistbtn.tag = "Add"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }
}

