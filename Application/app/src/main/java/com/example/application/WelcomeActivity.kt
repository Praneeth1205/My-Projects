package com.example.application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {


    var callbackManager : CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        callbackManager = CallbackManager.Factory.create()

        val loginBtn : Button = findViewById(R.id.loginBtn_welcome)
        val registerBtn : Button = findViewById(R.id.registerBtn_welcome)
       // val loginFb : Button = findViewById(R.id.login_fb_btn)

        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
       // loginBtn.setOnClickListener {
        //    Toast.makeText(this,"coming soon",Toast.LENGTH_SHORT).show()
        //}

    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser!=null){
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

    }
}