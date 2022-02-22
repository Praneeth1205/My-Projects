package com.blood.ease.learning.adapters

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.blood.ease.learning.R
import com.blood.ease.learning.ShowDetailsActivity
import com.blood.ease.learning.model.Source
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PlayerAdapter(private val mContext : Context,
                    private val videoList : ArrayList<Source>) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.list_videos_play, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlayerAdapter.ViewHolder, position: Int) {
        val video = videoList[position]

        holder.title.text = video.getTitle()

        setVideoUrl(video,holder)
    }

    private fun setVideoUrl(
        video: Source,
        holder: ViewHolder
    ) {

        holder.progressBar.visibility = View.VISIBLE

        val videoUrl : String = video.getVideoUrl()

        val mediaController  = MediaController(mContext)
        holder.videoView.setMediaController(mediaController)
        //holder.videoView.resolveAdjustedSize(100,180)

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

//        holder.back.setOnClickListener {
//            val intent = Intent(mContext,ShowDetailsActivity::class.java)
//            mContext.startActivity(intent)
//        }


    }
    override fun getItemCount(): Int {
        return videoList.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val videoView : VideoView = itemView.findViewById(R.id.video_play)
        val title : TextView = itemView.findViewById(R.id.text_video_name)
        val progressBar : ProgressBar = itemView.findViewById(R.id.progressbar)
       // val back : FloatingActionButton = itemView.findViewById(R.id.pip_mode_btn)

    }
}