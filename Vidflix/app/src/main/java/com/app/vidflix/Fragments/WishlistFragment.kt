package com.app.vidflix.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.vidflix.Adapters.VideoAdapter
import com.app.vidflix.Adapters.WishListAdapter
import com.app.vidflix.Models.Videos

import com.app.vidflix.R
import com.firebase.ui.database.paging.FirebaseDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_wishlist.*
import kotlinx.android.synthetic.main.list_videos_details.*


class WishlistFragment : Fragment() {

    private var addedList : MutableList<Videos>? = null
    private var wishListAdapter : WishListAdapter? = null

    private var firebaseUser : FirebaseUser? = null

    private var savedList : List<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_wishlist, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        recyclerview_wishlist?.setHasFixedSize(true)
        recyclerview_wishlist?.layoutManager = GridLayoutManager(context,2)

        addedList = ArrayList()

      //  myWishList()
        getAddedShows()

        return view
    }

    private fun getAddedShows() {
        val showRef = FirebaseDatabase.getInstance().reference
            .child("Wishlist").child(firebaseUser!!.uid)

        showRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (addedList as ArrayList<Videos>).clear()
                for (snap in snapshot.children){
                    val video = Videos()
                    video.setTitle(snap.child("title").value.toString())
                    video.setVidImageUrl(snap.child("vidImageUrl").value.toString())

                    (addedList as ArrayList<Videos>).add(video)

                    wishListAdapter = context?.let { WishListAdapter(it,addedList as ArrayList<Videos>) }
                    recyclerview_wishlist?.adapter = wishListAdapter

                    wishListAdapter?.notifyDataSetChanged()
                }
                for (item in addedList!!){
                    Log.d("list", "wishinfo:" + item.getVidImageUrl())
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }









}
