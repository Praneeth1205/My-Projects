package com.example.myapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapp.model.Category
import com.example.myapp.R
import com.example.myapp.adapter.CategoryAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private var categoryList: List<Category>? = ArrayList<Category>()
    private var categoryAdapter: CategoryAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerview_home?.setHasFixedSize(true)
        recyclerview_home?.layoutManager = GridLayoutManager(context,3)

        categoryList = ArrayList()

        getProducts()

        val accountsettings = view.findViewById<ImageView>(R.id.account_details)

        accountsettings!!.setOnClickListener {
            val fragment = AccountFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragment_container,fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        return view
    }

    private fun getProducts() {
        val ref = FirebaseDatabase.getInstance().reference
            .child("Products")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (categoryList as ArrayList<Category>).clear()

                for (snap in snapshot.children) {
                    val category = Category()
                    category.setImage(snap.child("imageUrl").value.toString())
                    category.setName(snap.child("name").value.toString())
                    (categoryList as ArrayList<Category>).add(category)
                }

                categoryAdapter = context?.let {
                    CategoryAdapter(
                        it,
                        categoryList as ArrayList<Category>
                    )
                }
                recyclerview_home?.adapter = categoryAdapter

                //categoryAdapter?.updateData(categoryList)
                for(item in categoryList!!){
                    Log.d("categoryListdata","dataInfo: "+item.getImage())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}

