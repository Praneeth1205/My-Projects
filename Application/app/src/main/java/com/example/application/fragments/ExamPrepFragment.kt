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


class ExamPrepFragment : Fragment() {

    private var examPrepAdapter : QuizAdapter? = null
    private var examPrepList : List<Data> = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exam_prep, container, false)

        val recyclerViewExamPrep : RecyclerView = view.findViewById(R.id.recyclerview_examPrep)

        recyclerViewExamPrep.layoutManager = LinearLayoutManager(context)
        recyclerViewExamPrep.setHasFixedSize(true)

        examPrepList = ArrayList()

        examPrepAdapter = context?.let { QuizAdapter(it,examPrepList as ArrayList<Data>) }
        recyclerViewExamPrep.adapter = examPrepAdapter

        displayExamPrep()

        return view
    }

    private fun displayExamPrep() {
        val ref : DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("ExamPrep")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (examPrepList as ArrayList<Data>).clear()
                for (snap in snapshot.children){
                    val data = Data()
                    data.setImageUrl(snap.child("imageUrl").value.toString())
                    data.setQuizTitle(snap.child("quizTitle").value.toString())
                    data.setEntryFee(snap.child("entryFee").value.toString())
                    data.setPrizeCoins(snap.child("prizeCoins").value.toString())
                    data.setUserCount(snap.child("userCount").value.toString())

                    (examPrepList as ArrayList<Data>).add(data)

                    examPrepAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}