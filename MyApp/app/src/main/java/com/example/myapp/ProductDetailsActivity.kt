package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapter.ItemAdapter
import com.example.myapp.model.Items
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.fragment_home.*

class ProductDetailsActivity : AppCompatActivity() {

    private var itemList : List<Items>? = null
    private var itemAdapter : ItemAdapter? = null
//    val checkOutbtn : Button? = findViewById(R.id.check_out_btn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        recyclerview_products?.setHasFixedSize(true)

        recyclerview_products?.layoutManager = LinearLayoutManager(this)

        itemList = ArrayList()

        getItemsList()

        check_out_btn?.setOnClickListener {
            startActivity(Intent(this,OrdersActivity::class.java))
        }

    }

    private fun getItemsList() {
        val bundle : Bundle? = intent.extras
        val title : String? = bundle?.getString("title")
        var firedata  = ""
        when(title){
            "Vegetables" -> firedata = "Vegetables"
            "Fruits" -> firedata = "Fruits"
            "Detergents" -> firedata = "Detergents"
            "Others" -> firedata = "Others"
            "DairyProducts" -> firedata = "DairyProducts"
            "Cereals" -> firedata = "Cereals"
            "Perfumes" -> firedata = "Perfumes"
            "HairOil" -> firedata = "HairOil"
            "ToothPaste" -> firedata = "ToothPaste"
        }
        val itemRef = FirebaseDatabase.getInstance().reference
            .child("Items").child(firedata)
        itemRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                (itemList as ArrayList<Items>).clear()
                for(snap in snapshot.children)
                {
                    val item = Items()
                    item.setItemImage(snap.child("itemimage").value.toString())
                    item.setItemName(snap.child("itemname").value.toString())
                    item.setMRP(snap.child("mrp").value.toString())
                    item.setId(snap.child("id").value.toString())
                    (itemList as ArrayList<Items>).add(item)
                }

                itemAdapter = ItemAdapter(this@ProductDetailsActivity,itemList as ArrayList<Items>)
                recyclerview_products?.adapter = itemAdapter

                for(item in itemList!!){
                    Log.d("itemListdata","dataInfo: "+item.getItemImage())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}