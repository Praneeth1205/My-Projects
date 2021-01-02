package Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socioapp.Adapter.NotificationAdapter
import com.example.socioapp.Model.Notification
import com.example.socioapp.Model.Post
import com.example.socioapp.Model.User
import com.example.socioapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_comments.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationsFragment : Fragment() {

    private var notificationList : List<Notification>? = null
    private var notificationAdapter : NotificationAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        val recyclerView : RecyclerView
        recyclerView= view.findViewById(R.id.recycler_view_notifications)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        notificationList = ArrayList()

        notificationAdapter = context?.let { NotificationAdapter(it,notificationList as ArrayList<Notification>) }
        recyclerView.adapter = notificationAdapter

        readNotifications()

        return  view
    }

    private fun readNotifications()
    {

        val notiRef = FirebaseDatabase.getInstance().reference
            .child("Notifications")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        notiRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                if(snapshot.exists())
                {
                    (notificationList as ArrayList<Notification>).clear()
                    for (snap in snapshot.children)
                    {
                        val notification = snap.getValue(Notification::class.java)
                        (notificationList as ArrayList<Notification>).add(notification!!)
                    }
                    Collections.reverse(notificationList)
                    notificationAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

}