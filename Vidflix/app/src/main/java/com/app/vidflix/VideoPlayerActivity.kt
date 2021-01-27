package com.app.vidflix

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.vidflix.Adapters.PlayerAdapter
import com.app.vidflix.Models.Videos
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : AppCompatActivity() {

    private  var videoList : MutableList<Videos>? = null
    private  var playerAdapter: PlayerAdapter? = null
    private var firedata : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        recyclerview_player.setHasFixedSize(true)
        recyclerview_player.layoutManager = LinearLayoutManager(this)

        videoList = ArrayList()

        playerAdapter = PlayerAdapter(this@VideoPlayerActivity,videoList as ArrayList<Videos>)

        recyclerview_player.adapter = playerAdapter

        PlayVideo()


    }

    private fun PlayVideo() {
        val bundle : Bundle = intent.extras!!
        val title : String? = bundle.getString("title")

        when(title) {
            "Andhadhun" -> firedata = "Andhadhun"
            "Aquaman" -> firedata = "Aquaman"
            "Ala Vaikuntapuramloo" -> firedata = "Ala Vaikuntapuramloo"
            "Bahubali" -> firedata = "Bahubali"
            "Bahubali 2" -> firedata = "Bahubali 2"
            "Bheeshma" -> firedata = "Bheeshma"
            "Despicable Me" -> firedata = "Despicable Me"
            "Descendants of the Sun" -> firedata = "Descendants of the Sun"
            "Ee Nagaraniki Emaindi" -> firedata = "Ee Nagaraniki Emaindi"
            "Extraction" -> firedata = "Extraction"
            "F.R.I.E.N.D.S" -> firedata = "FRIENDS"
            "My Id is Gangnam Beauty" -> firedata = "My Id is Gangnam Beauty"
            "Inter Steller" -> firedata = "Inter Steller"
            "Iteawon Class" -> firedata = "Iteawon Class"
            "Its Okay To Be Not Okay" -> firedata = "Its Okay To Be Not Okay"
            "Jhon Wick" -> firedata = "Jhon Wick"
            "My Love From Star" -> firedata = "My Love From Star"
            "Ludo" -> firedata = "Ludo"
            "Money Heist" -> firedata = "Money Heist"
            "Naam Shabana" -> firedata = "Naam Shabana"
            "Gunjan Saxena: The Kargil Girl" -> firedata = "Gunjan Saxena: The Kargil Girl"
            "Sacred Games" -> firedata = "Sacred Games"
            "Sherlock" -> firedata = "Sherlock"
            "StartUp" -> firedata = "StartUp"
            "Stranger Things" -> firedata = "Stranger Things"
            "The Vampire Diaries" -> firedata = "The Vampire Diaries"
        }
        val vidRef = FirebaseDatabase.getInstance().reference
            .child("Videos").child(firedata!!)

        vidRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (videoList as ArrayList<Videos>).clear()

                val video = snapshot.getValue(Videos::class.java)
                videoList!!.add(video!!)

                playerAdapter?.notifyDataSetChanged()

                for (item in videoList!!){
                    Log.d("listData", "videoinfo:" + item.getVideoUrl())
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
