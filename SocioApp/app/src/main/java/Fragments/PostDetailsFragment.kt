package Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socioapp.Adapter.PostAdapter
import com.example.socioapp.Model.Post
import com.example.socioapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_post_details.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostDetailsFragment : Fragment() {


    private var postAdapter : PostAdapter? = null
    private var postList : MutableList<Post>? = null
    private var postId : String? = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post_details, container, false)

       val preferences = context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)
        if (preferences!=null)
        {
            postId = preferences.getString("postId","none")
        }

        val recyclerView : RecyclerView = view.findViewById(R.id.recycler_view_post_details)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        postList = ArrayList()
        postAdapter = context?.let { PostAdapter(it,postList as ArrayList<Post>) }
        recyclerView.adapter =  postAdapter

        retrievePosts()
        return view
    }


    private fun retrievePosts() {
        val postsRef = FirebaseDatabase.getInstance()
            .reference.child("Posts")
            .child(postId!!)

        postsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                postList?.clear()
                val post = snapshot.getValue(Post::class.java)
                postList!!.add(post!!)
                postAdapter?.notifyDataSetChanged()

            }
            override fun onCancelled(error: DatabaseError)
            {

            }
        })
    }

}