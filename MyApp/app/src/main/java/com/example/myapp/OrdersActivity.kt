package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapter.OrderAdapter
import com.example.myapp.model.Orders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_orders.*
import kotlinx.android.synthetic.main.products_list_items.*
import java.util.*
import kotlin.collections.ArrayList

class OrdersActivity : AppCompatActivity() {

    private var orderList : List<Orders>? = null
    private var orderAdapter : OrderAdapter? = null
    private var firebaseUser : FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        recyclerview_cart?.setHasFixedSize(true)
        recyclerview_cart?.layoutManager = LinearLayoutManager(this@OrdersActivity)

        orderList = ArrayList()

        getOrders()
        place_order_btn?.setOnClickListener {
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }
        cancel_order_btn?.setOnClickListener {
            FirebaseDatabase.getInstance()
                .reference
                .child("Orders")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .removeValue()
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


    }

    private fun getOrders() {
        val orderRef = FirebaseDatabase.getInstance()
            .reference.child("Orders")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

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
                        order.setId(snap.child("id").value.toString())
                        (orderList as ArrayList<Orders>).add(order)
                    }
                    orderAdapter = OrderAdapter(this@OrdersActivity,orderList as ArrayList<Orders>)
                    recyclerview_cart.adapter = orderAdapter
                    Collections.reverse(orderList)
                    orderAdapter?.notifyDataSetChanged()


                    for(item in orderList!!){
                        Log.d("orderlist","dataInfo: "+item.getImageOfItem())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


}