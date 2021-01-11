package com.example.myapp.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.adapter.OfferAdapter
import com.example.myapp.model.Offer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_offers.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OffersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OffersFragment : Fragment() {

    private var offerimage : List<Offer>? = ArrayList()
    private var offerAdapter : OfferAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_offers, container, false)

        val recyclerView : RecyclerView = view.findViewById(R.id.recyclerview_offers)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        offerimage = ArrayList()
        getOffers()

        return view
    }

    private fun getOffers() {
        val offerRef = FirebaseDatabase.getInstance().reference
            .child("Offers")
        offerRef.addListenerForSingleValueEvent(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                (offerimage as ArrayList<Offer>).clear()
                for (snap in snapshot.children)
                {
                    val offer = Offer()
                    offer.setOfferImage(snap.child("offerImage").value.toString())
                    (offerimage as ArrayList<Offer>).add(offer)
                }
                offerAdapter = context?.let { OfferAdapter(it,offerimage as ArrayList<Offer>) }
                recyclerview_offers?.adapter = offerAdapter
                offerAdapter?.notifyDataSetChanged()
                for(item in offerimage!!){
                    Log.d("offerimages","dataInfo: "+item.getOfferImage())
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

}