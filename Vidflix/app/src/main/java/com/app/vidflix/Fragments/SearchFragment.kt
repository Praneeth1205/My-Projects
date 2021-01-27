package com.app.vidflix.Fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.vidflix.Adapters.SearchAdapter
import com.app.vidflix.Models.Shows
import com.app.vidflix.Models.Videos

import com.app.vidflix.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.search_videos.view.*

class SearchFragment : Fragment() {

    private var searchAdapter : SearchAdapter? = null
    private var videoList : MutableList<Shows>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerview_search)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        videoList = ArrayList()

        searchAdapter = context?.let { SearchAdapter(it,videoList as ArrayList<Shows>) }
        recyclerView.adapter = searchAdapter


        view.search_name?.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (view.search_name?.text.toString() == ""){

                }
                else{
                    recyclerView.visibility = View.VISIBLE
                    retriveVideos()
                    searchVideo(s.toString().toLowerCase())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })


        return view
    }

    private fun searchVideo(input: String) {
        val query = FirebaseDatabase.getInstance().reference
            .child("SliderImages")
            .orderByChild("showname")
            .startAt(input)
            //.endAt(search + "\uf8ff")


        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                videoList?.clear()
                for (snap in snapshot.children){
                    val video = snap.getValue(Shows::class.java)
                    if (video!=null){
                        videoList?.add(video)
                    }
                }
                searchAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun retriveVideos() {
        val vRef = FirebaseDatabase.getInstance().reference
            .child("SliderImages")

        vRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (view?.search_name?.text.toString() != ""){
                    videoList?.clear()
                    for (snap in snapshot.children){

                        val show = snap.getValue(Shows::class.java)
                        if (show!=null){
                            videoList?.add(show)
                        }
                    }
                    searchAdapter?.notifyDataSetChanged()
                }

                for (item in videoList!!){
                    Log.d("list", "searchinfo:" + item.getImageUrl())
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }



}
