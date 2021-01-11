package com.example.myapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.OrdersActivity
import com.example.myapp.R
import com.example.myapp.model.Items
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ItemAdapter(private val mContext : Context,
                  private val items : List<Items>
): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private var firebaseUser : FirebaseUser? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemname: TextView = itemView.findViewById(R.id.item_name)
        var mrp: TextView = itemView.findViewById(R.id.mrp_item)
        val itemimage: ImageView = itemView.findViewById(R.id.item_image)
        val quantity: TextView = itemView.findViewById(R.id.quantity_item)
        val addbtn: Button = itemView.findViewById(R.id.add_button)
        var itemscount: TextView = itemView.findViewById(R.id.count_item)
        val additems: ImageView = itemView.findViewById(R.id.add_items)
        val removeitems: ImageView = itemView.findViewById(R.id.remove_items)
      // val checkOutBtn : Button = itemView.findViewById(R.id.checkout_btn)
        @SuppressLint("SetTextI18n")
        fun bindItems(item: Items) {
            itemname.text = item.getItemName()
            mrp.text = "Rs." + item.getMRP()
            Picasso.get().load(item.getItemImage()).into(itemimage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.products_list_items, parent, false)

        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindItems(items[position])
        var totalitems = 0
        var count = 1
        holder.addbtn.setOnClickListener {
            holder.addbtn.visibility = View.GONE
            holder.additems.visibility = View.VISIBLE
            holder.removeitems.visibility = View.VISIBLE
            holder.itemscount.visibility = View.VISIBLE
            //holder.checkOutBtn.visibility = View.VISIBLE
            FirebaseDatabase.getInstance()
                .reference
                .child("Items")
            addItemsToCart(item.getItemName(), item.getMRP(), item.getItemImage(),item.getUID())


            holder.additems.setOnClickListener {
                count++
                holder.itemscount.text = count.toString()
                totalitems = count

            }
            holder.removeitems.setOnClickListener {
                if (count > 1) {
                    count--
                    holder.itemscount.text = count.toString()
                }
                else {
                    holder.addbtn.visibility = View.VISIBLE
                    holder.additems.visibility = View.GONE
                    holder.removeitems.visibility = View.GONE
                    holder.itemscount.visibility = View.GONE
                    //holder.checkOutBtn.visibility = View.GONE
                }
            }
            //holder.mrp = count.times(holder.mrp)
        }
    }



    private fun addItemsToCart(
        itemName: String,
        mrp: String,
        itemImage: String,
        uid: String
    ) {
        val itemsRef = FirebaseDatabase.getInstance().reference
            .child("Orders")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        val itemMap = HashMap<String,Any>()
        itemMap["nameofitem"] = itemName
        itemMap["mrpofitem"] = mrp
        itemMap["imageofitem"] = itemImage

        itemsRef.push().setValue(itemMap)

    }

}