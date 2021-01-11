package com.example.myapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.MainActivity
import com.example.myapp.R
import com.example.myapp.adapter.OrderAdapter
import com.example.myapp.model.Orders
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_orders.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.recyclerview_cart
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment() {
  //  private var orderList : List<Orders>? = null
   // private var orderAdapter : OrderAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
   /*     recyclerview_cart?.setHasFixedSize(true)
        recyclerview_cart?.layoutManager = LinearLayoutManager(context)

     //   orderList = ArrayList()

      //  getOrders()*/
        return view
    }

   /* private fun getOrders() {
        val orderRef = FirebaseDatabase.getInstance()
            .reference.child("Orders")

        orderRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    (orderList as ArrayList<Orders>).clear()
                    for(snap in snapshot.children)
                    {
                        val order = Orders()
                        order.setImageOfItem(snap.child("imageofitem").value.toString())
                        order.setMRPOfItem(snap.child("mrpofitem").value.toString())
                        order.setNameOFItem(snap.child("nameofitem").value.toString())
                        (orderList as ArrayList<Orders>).add(order)
                    }
                    orderAdapter = context?.let { OrderAdapter(it,orderList as ArrayList<Orders>) }
                    recyclerview_cart!!.adapter = orderAdapter
                 //   Collections.reverse(orderList)
                    orderAdapter?.notifyDataSetChanged()


                    for(item in orderList!!){
                        Log.d("listorders","dataInfo: "+item.getImageOfItem())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
*/

}