package com.blood.ease.learning.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blood.ease.learning.R
import com.blood.ease.learning.adapters.ShowsAdapter
import com.blood.ease.learning.model.Shows
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_video.*


class VideoFragment : Fragment() {

    private var showList : List<Shows> = ArrayList<Shows>()
    private var showsAdapter : ShowsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_video, container, false)

        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerView_videos)

        recyclerView.layoutManager = GridLayoutManager(context,2)

        showList = ArrayList()

        displayShows()

        return view
    }

    private fun displayShows() {
        val ref  = FirebaseDatabase.getInstance().reference.child("Videos")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                (showList as ArrayList<Shows>).clear()
                for (snap in snapshot.children){
                    val show = Shows()
                    show.setImageUrl(snap.child("imageUrl").value.toString())
                    show.setShowName(snap.child("showname").value.toString())
                    (showList as ArrayList<Shows>).add(show)

                    showsAdapter = context?.let {ShowsAdapter(it,showList as ArrayList<Shows>) }

                    recyclerView_videos?.adapter = showsAdapter

                    showsAdapter?.notifyDataSetChanged()

                }
                //

            }
        })
    }

}