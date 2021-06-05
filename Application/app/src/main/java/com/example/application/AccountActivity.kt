package com.example.application

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.application.models.User
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity() {

    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""
    private var myUrl = ""
    private var imageUri : Uri? = null
    private var storageProfilepicRef : StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        storageProfilepicRef = FirebaseStorage.getInstance().reference.child("Profile Pictures")


        back_btn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        updatePicBtn.setOnClickListener {
            checker = "clicked"

            CropImage.activity()
                .setAspectRatio(1,1)
                .start(this)
        }

        save_user_info.setOnClickListener {
            if (checker == "clicked"){
                uploadImageAndUpdateInfo()
            }
            else{
                updateUserInfoOnly()
            }
        }

        userInfo()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null)
        {
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            profile_image_edit.setImageURI(imageUri)
        }
    }

    private fun updateUserInfoOnly() {
        when {
            email_account.text.toString() == "" -> Toast.makeText(this,"Full Name is Empty",Toast.LENGTH_LONG).show()
            userName_account.text.toString() == "" -> Toast.makeText(this,"User Name is Empty",Toast.LENGTH_LONG).show()
            phone_number_acc.text.toString() == "" ->Toast.makeText(this,"Bio is Empty",Toast.LENGTH_LONG).show()

            else -> {
                val userRef = FirebaseDatabase.getInstance().reference.child("Users")

                val userMap = HashMap<String, Any>()
                userMap["phone"] = phone_number_acc.text.toString().toLowerCase()
                userMap["username"] = userName_account.text.toString().toLowerCase()
                userMap["email"] = email_account.text.toString().toLowerCase()

                userRef.child(firebaseUser.uid).updateChildren(userMap)

                Toast.makeText(this, "Profile is Updated", Toast.LENGTH_LONG).show()

                val intent = Intent(this@AccountActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun uploadImageAndUpdateInfo() {
        when{
            imageUri == null->Toast.makeText(this,"upload a pic",Toast.LENGTH_SHORT).show()
            userName_account.text.toString() == ""->Toast.makeText(this,"Enter userName",Toast.LENGTH_SHORT).show()
            email_account.text.toString() == ""->Toast.makeText(this,"Enter userName",Toast.LENGTH_SHORT).show()
            phone_number_acc.text.toString() == ""->Toast.makeText(this,"Enter userName",Toast.LENGTH_SHORT).show()

            else->{
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Account Settings")
                progressDialog.setMessage("Please wait, we are updating your profile...")
                progressDialog.show()

                val fileRef = storageProfilepicRef!!.child(firebaseUser!!.uid + ".jpg")

                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(imageUri!!)

                uploadTask.continueWithTask(Continuation { task ->
                    if (!task.isSuccessful)
                    {
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()

                        val ref = FirebaseDatabase.getInstance().reference.child("Users")

                        val userMap = HashMap<String, Any>()
                        userMap["phone"] = phone_number_acc.text.toString().toLowerCase()
                        userMap["username"] = userName_account.text.toString().toLowerCase()
                        userMap["email"] = email_account.text.toString().toLowerCase()
                        userMap["image"] = myUrl

                        ref.child(firebaseUser.uid).updateChildren(userMap)

                        Toast.makeText(
                            this,
                            "Account Information has been updated successfully.",
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        progressDialog.dismiss()
                    } else {
                        progressDialog.dismiss()
                    }

                }
            }
        }
    }

    private fun userInfo() {
        val userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snap: DataSnapshot)
            {
                if(snap.exists())
                {
                    val user = snap.getValue(User::class.java)

                    Picasso.get().load(user!!.getImageUrl()).placeholder(R.drawable.profile).into(profile_image_edit)
                    userName_account.setText(user.getUserName())
                    email_account.setText(user.getEmail())
                    phone_number_acc.setText(user.getPhone())

                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}
