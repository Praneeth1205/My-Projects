package com.example.application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.R
import com.example.application.adapters.QuizAdapter
import com.example.application.models.Data
import com.google.firebase.database.*


class CricketFragment : Fragment() {

    private var cricketAdapter : QuizAdapter? = null
    private var cricketList : List<Data> = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cricket, container, false)

        val recyclerViewCricket : RecyclerView = view.findViewById(R.id.recyclerview_cricket)

        recyclerViewCricket.layoutManager = LinearLayoutManager(context)
        recyclerViewCricket.setHasFixedSize(true)

        cricketList = ArrayList()

        cricketAdapter = context?.let { QuizAdapter(it,cricketList as ArrayList<Data>) }
        recyclerViewCricket.adapter = cricketAdapter

        displayCricket()

        return view
    }

    private fun displayCricket() {
        val ref : DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("Cricket")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (cricketList as ArrayList<Data>).clear()
                for (snap in snapshot.children){
                    val data = Data()
                    data.setImageUrl(snap.child("imageUrl").value.toString())
                    data.setQuizTitle(snap.child("quizTitle").value.toString())
                    data.setEntryFee(snap.child("entryFee").value.toString())
                    data.setPrizeCoins(snap.child("prizeCoins").value.toString())
                    data.setUserCount(snap.child("userCount").value.toString())

                    (cricketList as ArrayList<Data>).add(data)

                    cricketAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}