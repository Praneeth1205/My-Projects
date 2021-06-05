package com.example.application

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var progressDialog: ProgressDialog? = null

    private var mVerificationId : String = ""
    private var mResendCode : PhoneAuthProvider.ForceResendingToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        val sendCode: Button = findViewById(R.id.send_code)
        val phoneNumber: EditText = findViewById(R.id.phone_number_login)
        //val userName: EditText = findViewById(R.id.userName_login)

        sendCode.setOnClickListener {
            val phone = phoneNumber.text.toString()
            verifyCode(phone)

        }


    }

    private fun verifyCode(phoneNumber: String) {

        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(userName_login.toString())) {
            Toast.makeText(this, "Please enter your details", Toast.LENGTH_LONG).show()
        } else {
            progressDialog = ProgressDialog(this)
            progressDialog?.setTitle("Login")
            progressDialog?.setMessage("Please wait... This may take while...")
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.show()

            val options = PhoneAuthOptions.newBuilder(mAuth!!)
                .setPhoneNumber("+91$phoneNumber")
                .setActivity(this)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(mCallBack)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, token)

                mVerificationId = verificationId
                mResendCode = token

                Toast.makeText(this@LoginActivity,"Code has been sent",Toast.LENGTH_LONG).show()

              /*  val intent = Intent(this@LoginActivity, VerificationActivity::class.java)
                intent.putExtra("code", verificationId)
                intent.putExtra("username",userName_login.toString())
                intent.putExtra("phone",phone_number_login.toString())
                startActivity(intent)*/

            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@LoginActivity, "$p0", Toast.LENGTH_LONG).show()
                progressDialog?.dismiss()
            }
        }

}