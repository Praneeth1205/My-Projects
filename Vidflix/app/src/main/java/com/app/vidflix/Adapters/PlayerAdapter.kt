package com.app.vidflix.Adapters

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.app.vidflix.Models.Videos
import com.app.vidflix.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Created by Prakash Reddy on 1/23/2021.
 */
class PlayerAdapter(private val mContext : Context,
                    private val videoList : ArrayList<Videos>) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.video_list,parent,false))
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoList[position]

        holder.title.text = video.getTitle()

        setVideoUrl(video,holder)

    }

    private fun setVideoUrl(
        video: Videos,
        holder: ViewHolder
    ) {

        holder.progressBar.visibility = View.VISIBLE

        val videoUrl : String = video.getVideoUrl()

        val mediaController  = MediaController(mContext)
        holder.videoView.setMediaController(mediaController)
       // holder.videoView.resolveAdjustedSize(100,180)

        val videoUri : Uri = Uri.parse(videoUrl)
        mediaController.setAnchorView(holder.videoView)

        holder.videoView.setVideoURI(videoUri)
        holder.videoView.requestFocus()

        holder.videoView.setOnPreparedListener{mediaPlayer ->
            mediaPlayer.start()
        }

        holder.videoView.setOnInfoListener(MediaPlayer.OnInfoListener{mp,what,extra->

            when(what){
                MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START ->{
                    holder.progressBar.visibility = View.GONE
                    holder.title.visibility = View.GONE
                    return@OnInfoListener true
                }

                MediaPlayer.MEDIA_INFO_BUFFERING_START ->{
                    holder.progressBar.visibility = View.VISIBLE
                    return@OnInfoListener true
                }

                MediaPlayer.MEDIA_INFO_BUFFERING_END ->{
                    holder.progressBar.visibility = View.GONE
                    return@OnInfoListener true
                }
            }

            false
        })

        holder.videoView.setOnCompletionListener { mediaPlayer ->
            mediaPlayer.stop()
        }



    }

    class ViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView) {

        val videoView : VideoView = itemView.findViewById(R.id.video_play)
        val title : TextView = itemView.findViewById(R.id.text_video_name)
        val progressBar : ProgressBar = itemView.findViewById(R.id.progressbar)

    }
}