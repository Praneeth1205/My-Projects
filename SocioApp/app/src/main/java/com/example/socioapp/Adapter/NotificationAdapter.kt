package com.example.socioapp.Adapter

import Fragments.PostDetailsFragment
import Fragments.ProfileFragment
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.socioapp.Model.Notification
import com.example.socioapp.Model.Post
import com.example.socioapp.Model.User
import com.example.socioapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class   NotificationAdapter(private var mContext : Context,
                          private var mNotification : List<Notification>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.notifications_item_layout, parent, false))

    }

    override fun getItemCount(): Int {
       return mNotification.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val notification =  mNotification[position]

        if (notification.getText().equals("started following you "))
        {
            holder.text.text = "started following you "
        }
        else if (notification.getText().equals( "liked your post "))
        {
            holder.text.text = "liked your post "
        }
        else if (notification.getText().contains( "commented:"))
        {
            holder.text.text = notification.getText().replace("commented:","commented: ",false)
        }
        else
        {
            holder.text.text = notification.getText()
        }

        holder.itemView.setOnClickListener {
            if (notification.isIsPost())
            {
                val editor = mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit()

                editor.putString("postId",notification.getPostId())
                editor.apply()

                (mContext as FragmentActivity).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, PostDetailsFragment()).commit()

            }
            else
            {
                val editor = mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit()

                editor.putString("profileId",notification.getUserId())
                editor.apply()

                (mContext as FragmentActivity).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,ProfileFragment()).commit()

            }

        }



        userInfo(holder.profileImage,holder.userName,notification.getUserId())

        if(notification.isIsPost())
        {
            holder.postImage.visibility = View.VISIBLE
            getPostImage(holder.postImage,notification.getPostId())
        }
        else
        {
            holder.postImage.visibility = View.GONE

        }

    }

    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        var postImage: ImageView = itemView.findViewById(R.id.notifications__post_image)
        var profileImage: CircleImageView = itemView.findViewById(R.id.notifications__profile_image)
        var userName: TextView = itemView.findViewById(R.id.username_notification)
        var text: TextView = itemView.findViewById(R.id.comment_notification)

    }

    private fun userInfo(imageView: ImageView, userName : TextView, publisherId : String)
    {
        val userRef = FirebaseDatabase.getInstance()
            .reference.child("Users")
            .child(publisherId)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snap: DataSnapshot) {
                if (snap.exists()) {
                    val user = snap.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(imageView)
                    userName.text = user.getUsername()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun getPostImage(imageView: ImageView, postID:String)
    {
        val postRef = FirebaseDatabase.getInstance()
            .reference.child("Posts")
            .child(postID)
        postRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snap: DataSnapshot)
            {
                if(snap.exists())
                {
                    val post = snap.getValue<Post>(Post::class.java)

                    Picasso.get().load(post!!.getPostImage()).placeholder(R.drawable.profile).into(imageView)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }



}