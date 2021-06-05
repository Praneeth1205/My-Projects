package com.example.application

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit

class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var progressDialog: ProgressDialog? = null

    private var mVerificationId : String = ""
    private var mResendCode : PhoneAuthProvider.ForceResendingToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        next_btn.setOnClickListener {
            val phone = phone_number_register.text.toString()
            verifyNumber(phone)
        }

        verify_btn.setOnClickListener {
            codeVerification()
        }


    }


    private fun verifyNumber(phoneNumber: String) {

       when{
           phoneNumber==""-> Toast.makeText(this@RegisterActivity, "Please enter your phone number", Toast.LENGTH_LONG).show()
           username_register.toString() ==""-> Toast.makeText(this@RegisterActivity, "PLease enter your name", Toast.LENGTH_LONG).show()
           email_register.toString() == ""-> Toast.makeText(this@RegisterActivity, "Please enter you email", Toast.LENGTH_LONG).show()
           else-> {

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
    }

    private fun codeVerification() {
        phone_number_register.visibility = View.GONE
        username_register.visibility = View.GONE
        email_register.visibility = View.GONE

        val code = code_text.text.toString()

        if (code == ""){
             Toast.makeText(this@RegisterActivity, "Please enter the code", Toast.LENGTH_LONG).show()
        }else
        {
            progressDialog = ProgressDialog(this)
            progressDialog?.setTitle("Code Verification")
            progressDialog?.setMessage("Please wait... This may take while...")
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.show()

            val credential = PhoneAuthProvider.getCredential(mVerificationId, code)

            signInWithCredentials(credential)
        }
    }


    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, token)

                mVerificationId = verificationId
                mResendCode = token

                Toast.makeText(this@RegisterActivity, "Code Sent", Toast.LENGTH_LONG).show()

                phone_number_register.visibility = View.GONE
                username_register.visibility = View.GONE
                email_register.visibility = View.GONE

                verify_btn.visibility = View.VISIBLE
                code_text.visibility = View.VISIBLE

                progressDialog?.dismiss()
            }

            override fun onVerificationCompleted(phoneAuthCredentials: PhoneAuthCredential) {
                signInWithCredentials(phoneAuthCredentials)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@RegisterActivity, "$p0", Toast.LENGTH_LONG).show()
                phone_number_register.visibility = View.VISIBLE
                username_register.visibility = View.VISIBLE
                email_register.visibility = View.VISIBLE

                verify_btn.visibility = View.GONE
                code_text.visibility = View.GONE

                progressDialog?.dismiss()
            }

        }


    private fun signInWithCredentials(credential: PhoneAuthCredential
    ) {

        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateUserInfo(phone_number_register.toString(),username_register.toString(),email_register.toString(),progressDialog)
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                    progressDialog?.dismiss()
                }
            }

    }

    private fun updateUserInfo(phone: String, username: String, email: String, progressDialog: ProgressDialog?) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String,Any>()
        userMap["uid"] = currentUserId
        userMap["username"] = username.toLowerCase()
        userMap["email"] = email.toLowerCase()
        userMap["phone"] = phone.toLowerCase()

        userRef.child(currentUserId).setValue(userMap)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    progressDialog?.dismiss()
                    Toast.makeText(this,"Account has been created Successfully",Toast.LENGTH_LONG).show()

                    FirebaseDatabase.getInstance().reference.child("Users").child(currentUserId)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("username",username_register.text.toString())
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }else{
                    val message = task.exception!!.toString()
                    Toast.makeText(this,"Error:$message",Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog?.dismiss()
                }
            }
    }


}



