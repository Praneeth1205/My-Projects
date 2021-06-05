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


class ImageQuizFragment : Fragment() {

    private var imageQuizAdapter : QuizAdapter? = null
    private var imageQuizList : List<Data> = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_quiz, container, false)

        val recyclerViewImageQuiz : RecyclerView = view.findViewById(R.id.recyclerview_imageQuiz)

        recyclerViewImageQuiz.layoutManager = LinearLayoutManager(context)
        recyclerViewImageQuiz.setHasFixedSize(true)

        imageQuizList = ArrayList()

        imageQuizAdapter = context?.let { QuizAdapter(it,imageQuizList as ArrayList<Data>) }
        recyclerViewImageQuiz.adapter = imageQuizAdapter

        displayImageQuiz()

        return view
    }

    private fun displayImageQuiz() {
        val ref : DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("ImageQuiz")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (imageQuizList as ArrayList<Data>).clear()
                for (snap in snapshot.children){
                    val data = Data()
                    data.setImageUrl(snap.child("imageUrl").value.toString())
                    data.setQuizTitle(snap.child("quizTitle").value.toString())
                    data.setEntryFee(snap.child("entryFee").value.toString())
                    data.setPrizeCoins(snap.child("prizeCoins").value.toString())
                    data.setUserCount(snap.child("userCount").value.toString())

                    (imageQuizList as ArrayList<Data>).add(data)

                    imageQuizAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}