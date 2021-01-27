package com.app.vidflix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

            if (FirebaseAuth.getInstance().currentUser!=null){
                val intent = Intent(this@WelcomeActivity,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        else{
                login_btn!!.setOnClickListener {
                    val intent = Intent(this@WelcomeActivity,LoginActivity::class.java)
                    startActivity(intent)
                }
                signup_btn!!.setOnClickListener {
                    val intent = Intent(this@WelcomeActivity,SignupActivity::class.java)
                    startActivity(intent)
                }
            }
    }
}
