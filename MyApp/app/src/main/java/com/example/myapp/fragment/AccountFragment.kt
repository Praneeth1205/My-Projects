package com.example.myapp.fragment

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myapp.LoginActivity
import com.example.myapp.MainActivity
import com.example.myapp.R
import com.example.myapp.model.User
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
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_account.*
import java.util.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    private lateinit var firebaseUser: FirebaseUser
    private var checker = ""
    private var myUrl = ""
    private var imageUri : Uri? = null
    private var storageProfilepicRef : StorageReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        storageProfilepicRef = FirebaseStorage.getInstance().reference.child("Profile Pictures")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        val logoutbtn : Button = view.findViewById(R.id.logout_btn)
        val deleteAccountBtn : Button = view.findViewById(R.id.delete_account_btn)
        val changeImageText : TextView = view.findViewById(R.id.change_image_text_btn)
        val closebtn : ImageView = view.findViewById(R.id.close_btn)
        val savebtn : ImageView = view.findViewById(R.id.save_info_btn)


        logoutbtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

       /* deleteAccountBtn.setOnClickListener {
           val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Are you sure?")
            dialog.setMessage("Once account deleted it cannot be retrieved...")
            dialog.setPositiveButton("Delete",DialogInterface.OnClickListener { dialogInterface, i ->
                firebaseUser.delete().addOnCompleteListener { task ->
                    if (task.isSuccessful)
                    {
                        Toast.makeText(context,"Account Deleted Successfully",
                            Toast.LENGTH_LONG).show()

                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(context,task.exception?.message,Toast.LENGTH_LONG).show()
                    }
                }
            })
            dialog.setNegativeButton("Dismiss",DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            })
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.create()
            alertDialog.show()
        }*/

        changeImageText.setOnClickListener {
            checker = "clicked"

            context?.let { it ->
                CropImage.activity()
                    .start(it, this@AccountFragment)
            }
        }

        closebtn.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

        }

        savebtn.setOnClickListener {
            if(checker == "clicked")
            {
                uploadImageAndUpdateInfo()
            }
            else
            {
                updateUserInfoOnly()
            }

        }
        userInfo()


        return view
    }

    private fun userInfo()
    {
       // val profilePic : CircleImageView? = view?.findViewById(R.id.account_image)
        val userRef = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snap: DataSnapshot)
            {
                if(snap.exists())
                {
                    val user = snap.getValue<User>(User::class.java)

                    if (user!!.getProfileImage().isEmpty()) {
                        user.setProfileImage((R.drawable.profile).toString())
                    } else{
                        Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.profile).into(account_image)
                    }

                //
                    user_name_profile_frag.setText(user.getUserName())
                    full_name_profile_frag.setText(user.getFullName())
                   // bio_profile_frag.setText(user.getBio())

                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


    private fun updateUserInfoOnly()
    {

        when {
            full_name_profile_frag.text.toString() == "" -> Toast.makeText(context,"Full Name is Empty",Toast.LENGTH_LONG).show()
            user_name_profile_frag.text.toString() == "" -> Toast.makeText(context,"User Name is Empty",Toast.LENGTH_LONG).show()
           // bio_profile_frag.text.toString() == "" ->Toast.makeText(this,"Bio is Empty",Toast.LENGTH_LONG).show()

            else -> {
                val userRef = FirebaseDatabase.getInstance().getReference().child("Users")

                val userMap = HashMap<String, Any>()
                userMap["fullname"] = full_name_profile_frag.text.toString().toLowerCase()
                userMap["username"] = user_name_profile_frag.text.toString().toLowerCase()
                //userMap["bio"] = bio_profile_frag.text.toString().toLowerCase()

                userRef.child(firebaseUser.uid).updateChildren(userMap)

                Toast.makeText(context, "Profile is Updated", Toast.LENGTH_LONG).show()

                val intent = Intent(context, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
        }

    }


    private fun uploadImageAndUpdateInfo()
    {

        when {
            imageUri == null -> Toast.makeText(
                context,
                "Please Upload Your Profile Pic....",
                Toast.LENGTH_LONG
            ).show()
            full_name_profile_frag.text.toString() == "" -> Toast.makeText(
                context,
                "Full Name is Empty",
                Toast.LENGTH_LONG
            ).show()
            user_name_profile_frag.text.toString() == "" -> Toast.makeText(
                context,
                "User Name is Empty",
                Toast.LENGTH_LONG
            ).show()

            else -> {
                val progressDialog = ProgressDialog(context)
                progressDialog.setTitle("Account Settings")
                progressDialog.setMessage("Please wait, we are updating your profile...")
                progressDialog.show()

                val fileRef = storageProfilepicRef!!.child(firebaseUser!!.uid + ".jpg")

                var uploadTask: StorageTask<*>
                uploadTask = fileRef.putFile(imageUri!!)

                uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                    if (!task.isSuccessful)
                    {
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                }).addOnCompleteListener (OnCompleteListener<Uri> { task ->
                    if (task.isSuccessful)
                    {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()

                        val ref = FirebaseDatabase.getInstance().reference.child("Users")

                        val userMap = HashMap<String, Any>()
                        userMap["fullname"] = full_name_profile_frag.text.toString().toLowerCase(Locale.ROOT)
                        userMap["username"] = user_name_profile_frag.text.toString().toLowerCase(
                            Locale.ROOT)
                        userMap["image"] = myUrl

                        ref.child(firebaseUser.uid).updateChildren(userMap)

                        Toast.makeText(context, "Account Information has been updated successfully.", Toast.LENGTH_LONG).show()

                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        progressDialog.dismiss()
                    }
                    else
                    {
                        progressDialog.dismiss()
                    }
                } )
            }


        }


    }

}