package com.loaner.mobile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.fragment_more.*
import kotlinx.android.synthetic.main.fragment_more.view.*


class MoreFragment : Fragment() {

    lateinit var prefs: Prefs
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_more, container, false)

        prefs = Prefs(requireContext())

        view.logout?.setOnClickListener {
            prefs.isLoggined = false
            prefs.allDetailsFilled = false
            val intent = Intent(context,LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        view.privacy?.setOnClickListener {
            startActivity(Intent(context,PrivacypolicyActivity::class.java))
        }

        view.terms?.setOnClickListener {
            startActivity(Intent(context,TermsConditionsActivity::class.java))
        }

        view.about_us?.setOnClickListener {
            startActivity(Intent(context,AboutUsActivity::class.java))
        }

        view.contact_us?.setOnClickListener {
            startActivity(Intent(context,ContactUsActivity::class.java))
        }

        view.loan_history?.setOnClickListener {
            startActivity(Intent(context,LoanHistoryActivity::class.java))
        }
        return view
    }

}