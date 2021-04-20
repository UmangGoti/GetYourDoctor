package com.example.getyourdoctor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class Login : Fragment() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? =null
    var database: FirebaseDatabase? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        val fragmentTransaction = fragmentManager?.beginTransaction()
        view.loginBtn.setOnClickListener {

            auth = FirebaseAuth.getInstance()
            database = FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/")
            databaseReference = database?.reference!!.child("UserInfo")

            if (emailEditText.text.isNullOrEmpty() || passwordEditText.text.isNullOrEmpty())
                Toast.makeText(context,"Email Address or Password is not provided", Toast.LENGTH_SHORT).show()
            else {
                auth.signInWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        fragmentTransaction?.replace(R.id.homeframeLayout,ProfileMenu())
                        fragmentTransaction?.commit()
                        fragmentTransaction?.addToBackStack(null)
                        Toast.makeText(context,"Sign in Successful", Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(context,"Invalid Email or Password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        view.registerTv.setOnClickListener {
            fragmentTransaction?.replace(R.id.homeframeLayout,SignUp())
            fragmentTransaction?.remove(this)
            fragmentTransaction?.commit()
        }
        return view
    }

}