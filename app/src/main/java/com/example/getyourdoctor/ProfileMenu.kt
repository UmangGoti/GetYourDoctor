package com.example.getyourdoctor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.fragment_profile_menu.*
import kotlinx.android.synthetic.main.fragment_profile_menu.view.*

class ProfileMenu : Fragment() {

    lateinit var auth:FirebaseAuth
    var databaseReference: DatabaseReference? =null
    var database: FirebaseDatabase? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_profile_menu, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/")
        databaseReference = database?.reference!!.child("Patient")
        val currentuser = auth.currentUser
        if(currentuser != null){
        }

        view.loginBtn.setOnClickListener {
                if (emailEditText.text.isNullOrEmpty() || passwordEditText.text.isNullOrEmpty())
                    Toast.makeText(context,"Email Address or Password is not provided",Toast.LENGTH_SHORT).show()
                else {
                    auth.signInWithEmailAndPassword(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    ).addOnCompleteListener{ task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(context,UserInfoActivity::class.java))
                                Toast.makeText(context,"Sign in Successful", Toast.LENGTH_SHORT).show()
                            } else
                                Toast.makeText(context,"Invalid Email or Password", Toast.LENGTH_SHORT).show()
                        }
                }
            }

        view.registerTv.setOnClickListener {
            startActivity(Intent(context,RegisterActivity::class.java))
        }
        return view
    }
}