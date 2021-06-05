package com.example.application.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.application.R
import kotlinx.android.synthetic.main.fragment_invite_friends.view.*


class InviteFriendsFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_invite_friends, container, false)

        view.invite_frds.setOnClickListener {
            val intent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "Share App to:")
                this.type = "text/plain"
            }
            startActivity(intent)

        }

        return view
    }
}