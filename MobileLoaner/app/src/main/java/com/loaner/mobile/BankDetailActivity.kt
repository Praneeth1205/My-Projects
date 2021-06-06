package com.loaner.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.activity_bank_detail.*

class BankDetailActivity : AppCompatActivity() {

    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_detail)

        prefs = Prefs(this)

        submit_btn?.setOnClickListener {
            when{
                account_no.text.toString() == ""->Toast.makeText(this,"Please enter Account No.",Toast.LENGTH_SHORT).show()
                bank_name.text.toString() == ""->Toast.makeText(this,"Please enter  Bank Name",Toast.LENGTH_SHORT).show()
                branch_name.text.toString() == ""->Toast.makeText(this,"Please enter Branch Name",Toast.LENGTH_SHORT).show()
                ifsc_code.text.toString() == ""->Toast.makeText(this,"Please enter IFSC Code",Toast.LENGTH_SHORT).show()

                else->{
                    val intent = Intent(this,LoanDetailsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}