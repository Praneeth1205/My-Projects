package com.loaner.mobile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.*


class ProfileFragment : Fragment() {

    lateinit var prefs: Prefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        prefs = Prefs(requireContext())
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        view.dob?.setOnClickListener {
            val dpd = DatePickerDialog(requireContext(), { view, mYear, mMonth, mDay ->
                dob.setText("$mDay/$mMonth/$mYear")
            }, year, month, day)
            dpd.show()
        }

        view.next_btn.setOnClickListener {
            when {
                first_name.text.toString() == "" -> Toast.makeText(context, "Please enter First Name", Toast.LENGTH_SHORT).show()
                last_name.text.toString() == "" -> Toast.makeText(context, "Please enter Last Name", Toast.LENGTH_SHORT).show()
                //phone.text.toString() == "" -> Toast.makeText(context,"Please enter number",Toast.LENGTH_SHORT).show()
                email.text.toString() == "" -> Toast.makeText(context,"Please enter email",Toast.LENGTH_SHORT).show()
                dob.text.toString() == ""->Toast.makeText(context,"Please select date", Toast.LENGTH_SHORT).show()
                aadhar_no.text.toString() == "" -> Toast.makeText(context, "Please enter Aadhhar No", Toast.LENGTH_SHORT).show()
                pan_no.text.toString() == "" -> Toast.makeText(context,"Please enter Pan No", Toast.LENGTH_SHORT).show()
                else -> {
                    val intent = Intent(context, EducationDetailActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return view
    }
}