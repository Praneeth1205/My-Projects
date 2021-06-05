package com.example.application.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.application.R
import kotlinx.android.synthetic.main.fragment_language.view.*

class LanguageFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_language, container, false)

        view.change_btn?.setOnClickListener {
            dismiss()
        }

        return view
    }
}