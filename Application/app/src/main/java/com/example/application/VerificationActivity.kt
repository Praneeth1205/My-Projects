package com.example.application

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class VerificationActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        val code = intent.getStringExtra("code")

        mAuth = FirebaseAuth.getInstance()

        val verifyCodeBtn: Button = findViewById(R.id.verify_btn)
        val sentCode: EditText = findViewById(R.id.code_text)

        verifyCodeBtn.setOnClickListener {
            verifyCode(code, sentCode.text.toString())
        }


    }

    private fun verifyCode(authCode: String?, enteredCode: String) {

        val phone = intent.getStringExtra("phone")
        val username = intent.getStringExtra("username")

        if (TextUtils.isEmpty(authCode)) {
            Toast.makeText(this, "Please enter code", Toast.LENGTH_LONG).show()
        } else {

            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Login")
            progressDialog.setMessage("Please wait... This may take while...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            val credential = PhoneAuthProvider.getCredential(authCode!!, enteredCode)

            signInWithCredentials(credential, progressDialog,phone,username)
        }


    }

    private fun signInWithCredentials(
        credential: PhoneAuthCredential,
        progressDialog: ProgressDialog,
        phone: String?,
        username: String?
    ) {

        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateUserInfo(phone!!,username!!,progressDialog)
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                    progressDialog.dismiss()
                }
            }

    }


    private fun updateUserInfo(username: String, phone: String, progressDialog: ProgressDialog?) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String,Any>()
        userMap["uid"] = currentUserId
        userMap["username"] = username.toLowerCase()
        userMap["phone"] = phone.toLowerCase()

        userRef.child(currentUserId).setValue(userMap)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    progressDialog?.dismiss()

                    Toast.makeText(this,"Account has been created Successfully",Toast.LENGTH_LONG).show()

                    FirebaseDatabase.getInstance().reference.child("Users").child(currentUserId)

                    val intent = Intent(this@VerificationActivity, MainActivity::class.java)
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