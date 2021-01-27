package com.app.vidflix.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.vidflix.Adapters.ShowsAdapter
import com.app.vidflix.MainActivity
import com.app.vidflix.Models.Shows
import com.app.vidflix.R
import com.app.vidflix.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private var showList : List<Shows> = ArrayList<Shows>()
    private var showsAdapter : ShowsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val accountInfo : ImageView = view.findViewById(R.id.account_details)
        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerview_home)

        recyclerView.layoutManager = GridLayoutManager(context,2)

        showList = ArrayList()



        displayShows()

        accountInfo.setOnClickListener {
            val fragment = AccountFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTrans = fragmentManager?.beginTransaction()
            fragmentTrans?.replace(R.id.fragment_container,fragment)
            fragmentTrans?.addToBackStack(null)
            fragmentTrans?.commit()
        }

        return view
    }

    private fun displayShows() {
        val ref  = FirebaseDatabase.getInstance().reference.child("SliderImages")
        ref.addValueEventListener(object : ValueEventListener{
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

                    recyclerview_home?.adapter = showsAdapter

                    showsAdapter?.notifyDataSetChanged()

                }
              //

            }
        })
    }


}
