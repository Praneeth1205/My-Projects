package com.example.myapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.MainActivity
import com.example.myapp.OrdersActivity
import com.example.myapp.PaymentActivity
import com.example.myapp.R
import com.example.myapp.model.Orders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class OrderAdapter (private val mContext : Context,
                    private val orderItemsList: List<Orders>
) : RecyclerView.Adapter<OrderAdapter.ViewHolder>()
{
    private var firebaseUser : FirebaseUser? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val itemname : TextView = itemView.findViewById(R.id.item_name_cart)
        val itemmrp : TextView = itemView.findViewById(R.id.total_mrp)
        val itemimage : ImageView = itemView.findViewById(R.id.item_image_cart)
       // val placeOrder : Button = itemView.findViewById(R.id.place_order_btn)
        //val cancelOrder : Button = itemView.findViewById(R.id.cancel_order_btn)
        //val total : TextView = itemView.findViewById(R.id.total)
        //val totalcost : TextView = itemView.findViewById(R.id.total_cost_items)
        val items : TextView = itemView.findViewById(R.id.items)
        var itemCount : TextView = itemView.findViewById(R.id.items_count)

        @SuppressLint("SetTextI18n")
        fun bind(orders: Orders)
        {
            itemname.text = orders.getNameOFItem()
            itemmrp.text = "Rs." + orders.getMRPOfItem()

            Picasso.get().load(orders.getImageOfItem()).into(itemimage)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.ordered_items,parent,false))

    }

    override fun getItemCount(): Int {
       return orderItemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        orderItemsList[position]
        holder.bind(orderItemsList[position])


       // getOrderDetails(holder.itemimage,holder.itemmrp,holder.itemname)
    }

}