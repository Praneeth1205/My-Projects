package com.app.vidflix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.vidflix.Adapters.VideoAdapter
import com.app.vidflix.Models.Videos
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_show_details.*
import kotlinx.android.synthetic.main.list_videos_details.*
import java.util.*
import kotlin.collections.ArrayList

class ShowDetailsActivity : AppCompatActivity() {

    private  var videosList : MutableList<Videos>? = null
    private var videoAdapter : VideoAdapter? = null
    private var firedata : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)

        recyclerview_videos.setHasFixedSize(true)
        recyclerview_videos.layoutManager = LinearLayoutManager(this)

        videosList = ArrayList()

        videoAdapter = VideoAdapter(this@ShowDetailsActivity,videosList as ArrayList<Videos>)
        recyclerview_videos?.adapter = videoAdapter

        wishlist_btn?.setOnClickListener {
            wishlist_btn.visibility = View.GONE
            remove_wishlist_btn.visibility = View.VISIBLE
        }

        remove_wishlist_btn?.setOnClickListener {
            wishlist_btn.visibility = View.VISIBLE
            remove_wishlist_btn.visibility = View.GONE
        }


        getVideoDetails()
    }

    private fun getVideoDetails() {

        val bundle : Bundle? = intent.extras
        val showname : String? = bundle?.getString("showname")

        when(showname){
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

        vidRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                videosList?.clear()
                val video = snapshot.getValue(Videos::class.java)
                videosList!!.add(video!!)

                videoAdapter?.notifyDataSetChanged()

                for (item in videosList!!){
                    Log.d("listData", "datainfo:" + item.getVidImageUrl())
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }


        })

    }
}