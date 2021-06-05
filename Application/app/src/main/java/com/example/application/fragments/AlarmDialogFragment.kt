package com.example.application.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.application.R
import kotlinx.android.synthetic.main.fragment_alarm_dialog.*
import kotlinx.android.synthetic.main.fragment_alarm_dialog.view.*


class AlarmDialogFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_alarm_dialog, container, false)

        view.cancel_btn?.setOnClickListener {
            dismiss()
        }

        view.submit_btn?.setOnClickListener {
            Toast.makeText(context,"Submitted....",Toast.LENGTH_SHORT).show()
        }

        return view
    }

}