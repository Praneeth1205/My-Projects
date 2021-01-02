package com.example.socioapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socioapp.Adapter.CommentsAdapter
import com.example.socioapp.Model.Comment
import com.example.socioapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_comments.*

class CommentsActivity : AppCompatActivity() {

    private var postId = ""
    private var publisherId = ""
    private var firebaseUser : FirebaseUser? = null
    private var commentsAdapter : CommentsAdapter? = null
    private var commentList : MutableList<Comment>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        val intent = intent
        postId = intent.getStringExtra("postId")!!
        publisherId = intent.getStringExtra("publisherId")!!

        firebaseUser = FirebaseAuth.getInstance().currentUser

        var recyclerView : RecyclerView = findViewById(R.id.recycler_view_comments)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        recyclerView.layoutManager = linearLayoutManager

        commentList = ArrayList()
        recyclerView.adapter = CommentsAdapter(this,commentList)

        userInfo()
        readComments()
        getPostImage()

        post_comment.setOnClickListener(View.OnClickListener{
            if (add_comment!!.text.toString() == "")
            {
                Toast.makeText(this@CommentsActivity,"Please enter the comment...",Toast.LENGTH_LONG).show()
            }
            else
            {
                addComment()
            }
        })
    }

    private fun addComment() {
        val commentsRef = FirebaseDatabase.getInstance().reference
            .child("Comments")
            .child(postId!!)

        val commentsMap = HashMap<String,Any>()
        commentsMap["comment"] = add_comment!!.text.toString()
        commentsMap["publisher"] = firebaseUser!!.uid

        commentsRef.push().setValue(commentsMap)

        addNotification()

        add_comment!!.text.clear()

    }

    private fun userInfo()
    {
        val userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)
        userRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snap: DataSnapshot)
            {
                if(snap.exists())
                {
                    val user = snap.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(profile_image_comment)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun getPostImage()
    {
        val postRef = FirebaseDatabase.getInstance()
            .reference.child("Posts")
            .child(postId).child("postimage")
        postRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snap: DataSnapshot)
            {
                if(snap.exists())
                {
                    val image = snap.value.toString()

                    Picasso.get().load(image).placeholder(R.drawable.profile).into(post_image_comment)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }



    private fun readComments()
    {
        val commentsRef = FirebaseDatabase.getInstance().reference
            .child("Comments")
            .child(postId)

        commentsRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if(snapshot.exists())
                {
                    commentList!!.clear()
                    for (snap in snapshot.children)
                    {
                        val comment = snap.getValue(Comment::class.java)
                        commentList!!.add(comment!!)
                    }
                    commentsAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun addNotification()
    {
        val notiRef =FirebaseDatabase.getInstance().reference
            .child("Notifications")
            .child(publisherId!!)

        val notiMap = HashMap<String,Any>()
       notiMap["userid"] = firebaseUser!!.uid
       notiMap["text"] = "commented: " + add_comment!!.text.toString()
       notiMap["postid"] = postId
       notiMap["ispost"] = true

        notiRef.push().setValue(notiMap)

    }
}