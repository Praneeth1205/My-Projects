package com.blood.ease.learning

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.blood.ease.learning.adapters.PlayerAdapter
import com.blood.ease.learning.model.Source
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : AppCompatActivity() {

    private  var videoList : MutableList<Source>? = null
    private  var playerAdapter: PlayerAdapter? = null
    private var firedata = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        recyclerview_player.setHasFixedSize(true)
        recyclerview_player.layoutManager = LinearLayoutManager(this)

        videoList = ArrayList()



        playVideo()

    }

    private fun playVideo() {

        val bundle : Bundle = intent.extras!!
        val title : String? = bundle.getString("title")


        val vidRef = FirebaseDatabase.getInstance().reference
            .child("Source").child(title!!)

        vidRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (videoList as ArrayList<Source>).clear()

//                val video = snapshot.getValue(Source::class.java)
//                videoList!!.add(video!!)
                for (snap in snapshot.children){
                    val source = Source()
                    source.setVideoUrl(snap.child("videoUrl").value.toString())
                    (videoList as ArrayList<Source>).add(source)

                    playerAdapter = PlayerAdapter(this@VideoPlayerActivity,videoList as ArrayList<Source>)
                    recyclerview_player.adapter = playerAdapter

                    playerAdapter?.notifyDataSetChanged()

                }


                for (item in videoList!!){
                    Log.d("data", "videoinfo:" + item.getVideoUrl())
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onStart() {
        super.onStart()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}