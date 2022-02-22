package com.blood.ease.learning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.blood.ease.learning.adapters.VideoAdapter
import com.blood.ease.learning.model.Source
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_details.*
import kotlinx.android.synthetic.main.details_file.*

class ShowDetailsActivity : AppCompatActivity() {

    private  var videosList : MutableList<Source>? = null
    private var videoAdapter : VideoAdapter? = null
    //private var firedata =""
   // private var imageVid : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)

        recyclerview_details.setHasFixedSize(true)
        recyclerview_details.layoutManager = LinearLayoutManager(this)


        videosList = ArrayList()

//        videoAdapter = VideoAdapter(this@ShowDetailsActivity,videosList as ArrayList<Source>)
//        recyclerview_details?.adapter = videoAdapter

        getVideoDetails()

    }

    private fun getVideoDetails() {
      //  imageVid = findViewById(R.id.image_video)
        val bundle : Bundle? = intent.extras
        val showname : String? = bundle?.getString("showname")
       // val imgUrl = bundle?.getString("url")

        title_video?.text = showname


      //  Picasso.get().load(imgUrl).into(image_video)

        val vidRef = FirebaseDatabase.getInstance().reference
            .child("Source").child(showname!!)

        vidRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                (videosList as ArrayList<Source>).clear()
                for (snap in snapshot.children){
                    val video = Source()
                    video.setImageUrl(snap.child("imageUrl").value.toString())
                   video.setTitle(snap.child("title").value.toString())
                    video.setDescription(snap.child("description").value.toString())
                    video.setAuthor(snap.child("author").value.toString())
                    (videosList as ArrayList<Source>).add(video)

                    videoAdapter = VideoAdapter(this@ShowDetailsActivity,videosList as ArrayList<Source>)
                    recyclerview_details?.adapter = videoAdapter

                    videoAdapter?.notifyDataSetChanged()

                }
                for (item in videosList!!){
                    Log.d("listData", "datainfo:" + item.getImageUrl())
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }


        })

    }
}