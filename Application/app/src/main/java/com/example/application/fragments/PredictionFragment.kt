package com.example.application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.R
import com.example.application.adapters.PredictAdapter
import com.example.application.models.Data
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PredictionFragment : Fragment() {

    private var predictAdapter : PredictAdapter? = null
    private var predictList : List<Data> = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_prediction, container, false)

        val recyclerViewPredict : RecyclerView = view.findViewById(R.id.recyclerview_predict)
        recyclerViewPredict.layoutManager = LinearLayoutManager(context)
        recyclerViewPredict.setHasFixedSize(true)

        predictList = ArrayList()

        predictAdapter = context?.let { PredictAdapter(it,predictList as ArrayList<Data>) }
        recyclerViewPredict.adapter = predictAdapter

        displayPredicts()

        return view
    }

    private fun displayPredicts() {
        val ref = FirebaseDatabase.getInstance().reference.child("Prediction")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (predictList as ArrayList<Data>).clear()
                for (snap in snapshot.children){
                    val data = Data()
                    data.setImageUrl(snap.child("imageUrl").value.toString())
                    data.setImageUrlTwo(snap.child("imageUrlTwo").value.toString())
                    data.setQuizTitle(snap.child("quizTitle").value.toString())
                    data.setEntryFee(snap.child("entryFee").value.toString())
                    data.setPrizeCoins(snap.child("prizeCoins").value.toString())
                    data.setUserCount(snap.child("userCount").value.toString())
                    data.setParticipantOne(snap.child("participantOne").value.toString())
                    data.setParticipantTwo(snap.child("participantTwo").value.toString())

                    (predictList as ArrayList<Data>).add(data)
                    
                    predictAdapter?.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}