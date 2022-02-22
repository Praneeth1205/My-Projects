package com.blood.ease.learning.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blood.ease.learning.R
import com.blood.ease.learning.adapters.QuestionAdapter
import com.blood.ease.learning.model.Question
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_ebook.*


class EbookFragment : Fragment() {

    private var qlist : List<Question> = ArrayList<Question>()
    private var questionAdapter : QuestionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ebook, container, false)

        val recyclerViewQuestion : RecyclerView = view.findViewById(R.id.recyclerView_questions)
        recyclerViewQuestion.layoutManager = LinearLayoutManager(context)

        qlist = ArrayList()

        displayQuestions()

        return view
    }

    private fun displayQuestions() {
        val ref = FirebaseDatabase.getInstance().reference.child("Questions")

        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (qlist as ArrayList<Question>).clear()
                for (snap in snapshot.children){
                    val ques = Question()

                    ques.setQuestion(snap.child("question").value.toString())
                    ques.setOption1(snap.child("option1").value.toString())
                    ques.setOption2(snap.child("option2").value.toString())
                    ques.setOption3(snap.child("option3").value.toString())
                    ques.setOption4(snap.child("option4").value.toString())
                    ques.setAnswer(snap.child("answer").value.toString())

                    (qlist as ArrayList<Question>).add(ques)

                    questionAdapter = context?.let { QuestionAdapter(it, qlist as ArrayList<Question>)}

                    recyclerView_questions?.adapter = questionAdapter

                    questionAdapter?.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}