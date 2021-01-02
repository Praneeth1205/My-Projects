package Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socioapp.AccountSettingsActivity
import com.example.socioapp.Adapter.MyImageAdapter
import com.example.socioapp.Model.Post
import com.example.socioapp.Model.User
import com.example.socioapp.R
import com.example.socioapp.ShowUsersActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    private lateinit var profileId: String
    private lateinit var firebaseUser: FirebaseUser

    var postList: List<Post>? = null
    var myImageAdapter: MyImageAdapter? = null

    var myImageAdapterSavedImg: MyImageAdapter? = null
    var postListSavedImg: List<Post>? = null
    var mySavesImg: List<String>? = null



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        if (pref != null) {
            this.profileId = pref.getString("profileId", "none")!!
        }

        if (profileId == firebaseUser.uid) {
            view.edit_account_settings_btn.text = "Edit Profile"
        } else if (profileId != firebaseUser.uid) {
            checkFollowFollowing()
        }

       //recyclerview for uploaded photos
        var recyclerViewUploadImages: RecyclerView
        recyclerViewUploadImages = view.findViewById(R.id.recycler_view_upload_pic)
        recyclerViewUploadImages.setHasFixedSize(true)
        val linearLayoutManager: LinearLayoutManager = GridLayoutManager(context, 3)
        recyclerViewUploadImages.layoutManager = linearLayoutManager

        postList = ArrayList()
        recyclerViewUploadImages.adapter = context?.let { MyImageAdapter(it, postList as ArrayList<Post>) }

        //recyclerview for saved photos
        var recyclerViewSavedImages: RecyclerView
        recyclerViewSavedImages = view.findViewById(R.id.recycler_view_saved_pic)
        recyclerViewSavedImages.setHasFixedSize(true)
        val linearLayoutManager2: LinearLayoutManager = GridLayoutManager(context, 3)
        recyclerViewSavedImages.layoutManager = linearLayoutManager2

        postListSavedImg = ArrayList()
        recyclerViewSavedImages.adapter = context?.let { MyImageAdapter(it, postListSavedImg as ArrayList<Post>) }

        recyclerViewSavedImages.visibility = View.GONE
        recyclerViewUploadImages.visibility = View.VISIBLE

        val uploadedImagesBtn : ImageButton = view.findViewById(R.id.image_grid_view_btn)
        uploadedImagesBtn.setOnClickListener {
            recyclerViewSavedImages.visibility = View.GONE
            recyclerViewUploadImages.visibility = View.VISIBLE
        }

        val uploadedSavedBtn : ImageButton = view.findViewById(R.id.image_save_btn)
        uploadedSavedBtn.setOnClickListener {
            recyclerViewSavedImages.visibility = View.VISIBLE
            recyclerViewUploadImages.visibility = View.GONE
        }

        view.total_followers.setOnClickListener {
            val intent = Intent(context,ShowUsersActivity::class.java)
            intent.putExtra("id",profileId)
            intent.putExtra("title","followers")
            startActivity(intent)
        }

        view.total_following.setOnClickListener {
            val intent = Intent(context,ShowUsersActivity::class.java)
            intent.putExtra("id",profileId)
            intent.putExtra("title","following")
            startActivity(intent)
        }



        view.edit_account_settings_btn.setOnClickListener {
            val getButtonText = view.edit_account_settings_btn.text.toString()

            when (getButtonText) {
                "Edit Profile" -> startActivity(
                    Intent(
                        context,
                        AccountSettingsActivity::class.java
                    )
                )
                "Follow" -> {
                    firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow")
                            .child(it1)
                            .child("Following").child(profileId)
                            .setValue(true)
                    }
                    firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow")
                            .child(profileId)
                            .child("Followers").child(it1)
                            .setValue(true)
                    }

                    addNotification()

                }
                "Following" -> {
                    firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow")
                            .child(it1)
                            .child("Following").child(profileId)
                            .removeValue()
                    }
                    firebaseUser.uid.let { it1 ->
                        FirebaseDatabase.getInstance().reference
                            .child("Follow")
                            .child(profileId)
                            .child("Followers").child(it1)
                            .removeValue()
                    }

                }
            }


        }

        getFollowers()
        getFollowings()
        userInfo()
        myPhotos()
        getNumberOfPosts()
        mySaves()

        return view
    }

    private fun checkFollowFollowing() {
        val followingRef = firebaseUser.uid.let {

            FirebaseDatabase.getInstance().reference
                .child("Follow")
                .child(it)
                .child("Following")
        }

        followingRef.addValueEventListener(object : ValueEventListener {

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(profileId).exists()) {
                    view?.edit_account_settings_btn?.text = "Following"
                } else {
                    view?.edit_account_settings_btn?.text = "Follow"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun getFollowers() {
        val followersRef = FirebaseDatabase.getInstance().reference
            .child("Follow")
            .child(profileId)
            .child("Followers")

        followersRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    view?.total_followers?.text = snapshot.childrenCount.toString()
                }
            }


        })
    }

    private fun getFollowings() {
        val followersRef = FirebaseDatabase.getInstance().reference
            .child("Follow")
            .child(profileId)
            .child("Following")

        followersRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    view?.total_following?.text = snapshot.childrenCount.toString()
                }
            }


        })
    }

    private fun myPhotos() {
        val postRef = FirebaseDatabase.getInstance().reference.child("Posts")

        postRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    (postList as ArrayList<Post>).clear()

                    for (snap in snapshot.children) {
                        val post = snap.getValue(Post::class.java)!!
                        if (post.getPublisher().equals(profileId)) {
                            (postList as ArrayList<Post>).add(post)
                        }
                        Collections.reverse(postList)
                        myImageAdapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun userInfo() {
        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(profileId)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snap: DataSnapshot) {
                if (snap.exists()) {
                    val user = snap.getValue<User>(User::class.java)

                    Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile)
                        .into(view?.pro_image_profile_frag)
                    view?.profile_fragment_username?.text = user!!.getUsername()
                    view?.full_name_profile_frag?.text = user!!.getFullname()
                    view?.bio_profile_frag?.text = user!!.getBio()

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun onStop() {
        super.onStop()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    override fun onPause() {
        super.onPause()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    private fun getNumberOfPosts() {
        val postsRef = FirebaseDatabase.getInstance().reference
            .child("Posts")

        postsRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if(snapshot.exists())
                {
                    var postCounter = 0
                    for (snap in snapshot.children)
                    {
                        val post = snap.getValue(Post::class.java)!!
                        if(post.getPublisher() == profileId)
                        {
                            postCounter++
                        }
                    }
                    total_posts.text = "  $postCounter"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun mySaves()
    {
        mySavesImg = ArrayList()

        val savedRef = FirebaseDatabase.getInstance()
            .reference.child("Saves").child(firebaseUser.uid)

        savedRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if(snapshot.exists())
                {
                    for(snap in snapshot.children)
                    {
                        (mySavesImg as ArrayList<String>).add(snap.key!!)
                    }
                    readSavedImagesData()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun readSavedImagesData()
    {
        val postRef = FirebaseDatabase.getInstance().reference.child("Posts")

        postRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if (snapshot.exists()) {
                    (postListSavedImg as ArrayList<Post>).clear()
                    for (snap in snapshot.children)
                    {
                        val post = snap.getValue(Post::class.java)!!
                        for (key in mySavesImg!!)
                        {
                            if(post!!.getPostid() == key)
                            {
                                (postListSavedImg as ArrayList<Post>).add(post!!)

                            }


                        }
                    }
                    myImageAdapter?.notifyDataSetChanged()
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
            .child(profileId)

        val notiMap = HashMap<String,Any>()
        notiMap["userid"] = firebaseUser!!.uid
        notiMap["text"] = "started following you "
        notiMap["postid"] = ""
        notiMap["ispost"] = false

        notiRef.push().setValue(notiMap)

    }
}