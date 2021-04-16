package com.example.getyourdoctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfoActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? =null
    var database: FirebaseDatabase? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/")
        databaseReference = database?.reference!!.child("Patient")
        loadprofile()
    }
    private fun loadprofile(){
        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)
        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userName.text= "UserName ->"+snapshot.child("nameof").value.toString()
                contactNum.text= "UserContact ->"+snapshot.child("mobileof").value.toString()
                dateofBirth.text= "Date of Birth ->"+snapshot.child("dateof").value.toString()
                userEmailId.text= "UserEmailID ->"+snapshot.child("emailof").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FirebaseAuth.getInstance().signOut()
    }
}