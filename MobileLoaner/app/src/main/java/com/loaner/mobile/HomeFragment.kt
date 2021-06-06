package com.loaner.mobile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    lateinit var prefs: Prefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        prefs = Prefs(requireContext())

        view.apply_now?.setOnClickListener {
            if (prefs.allDetailsFilled!=null && prefs.allDetailsFilled!!){
                val intent = Intent(context,LoanDetailsActivity::class.java)
                startActivity(intent)
            }else{
                val fragmentTrans = fragmentManager?.beginTransaction()
                fragmentTrans?.replace(R.id.fragment_container, ProfileFragment())
                fragmentTrans?.commit()
            }
        }

        return view
    }
}