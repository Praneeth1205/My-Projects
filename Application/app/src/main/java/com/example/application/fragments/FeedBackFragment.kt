package com.example.application.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.application.R
import kotlinx.android.synthetic.main.fragment_feed_back.*
import kotlinx.android.synthetic.main.fragment_feed_back.view.*

class FeedBackFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_feed_back, container, false)


        view.cancel_feed_btn?.setOnClickListener {
            dismiss()
        }

        view.submit_feed_btn?.setOnClickListener {
            if (message_feedback.toString()== ""){
                Toast.makeText(context,"please write something....",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"feedback submitted successfully....",Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}