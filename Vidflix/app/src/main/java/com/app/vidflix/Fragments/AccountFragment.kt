package com.app.vidflix.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.app.vidflix.MainActivity
import com.app.vidflix.Models.User

import com.app.vidflix.R
import com.app.vidflix.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_account.*


class AccountFragment : Fragment() {

    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val view = inflater.inflate(R.layout.fragment_account, container, false)


        val logoutbtn : Button = view.findViewById(R.id.logout_btn)
        val closebtn : ImageView = view.findViewById(R.id.back_btn)
    //    val fullname : EditText = view.findViewById(R.id.fullname_account)
      //  val username : EditText = view.findViewById(R.id.username_account)
        //val email : EditText = view.findViewById(R.id.email_account)

        logoutbtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(context,WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        closebtn.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

        }

        displayUserInfo()

        return view
    }

    private fun displayUserInfo() {

        val userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser.uid)

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snap: DataSnapshot) {
                if (snap.exists()){
                    val user = snap.getValue<User>(User::class.java)
                    fullname_account!!.setText(user!!.getFullName())
                    username_account!!.setText(user.getUserName())
                    email_account!!.setText(user.getEmail())
                }

            }

        })
    }



}
