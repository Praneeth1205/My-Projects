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
import kotlinx.android.synthetic.main.fragment_play_quiz.*
import kotlinx.android.synthetic.main.fragment_play_quiz.view.*

class PlayQuizFragment : Fragment() {

    private var quizAdapter : QuizAdapter? = null
    private var quizList : List<Data> = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play_quiz, container, false)

        val recyclerViewQuiz : RecyclerView = view.findViewById(R.id.recyclerview_play_quiz)

        recyclerViewQuiz.layoutManager = LinearLayoutManager(context)
        recyclerViewQuiz.setHasFixedSize(true)

        quizList = ArrayList()

        quizAdapter = context?.let { QuizAdapter(it,quizList as ArrayList<Data>) }
        recyclerViewQuiz.adapter = quizAdapter

        displayQuizList()

        view.view_show_timings?.setOnClickListener {
            val dialog = ShowTimingsFragment()

            dialog.show(requireFragmentManager(),"customDialog")
        }

        return view
    }

    private fun displayQuizList() {
        val ref : DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("Quiz")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (quizList as ArrayList<Data>).clear()
                for (snap in snapshot.children){
                    val data = Data()
                    data.setImageUrl(snap.child("imageUrl").value.toString())
                    data.setQuizTitle(snap.child("quizTitle").value.toString())
                    data.setEntryFee(snap.child("entryFee").value.toString())
                    data.setPrizeCoins(snap.child("prizeCoins").value.toString())
                    data.setUserCount(snap.child("userCount").value.toString())

                    (quizList as ArrayList<Data>).add(data)

                    quizAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}