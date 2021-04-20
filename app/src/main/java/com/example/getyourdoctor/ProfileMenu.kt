package com.example.getyourdoctor


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile_menu.view.*
import java.text.SimpleDateFormat
import java.util.*

class ProfileMenu : Fragment() {

    lateinit var auth:FirebaseAuth
    var databaseReference: DatabaseReference? =null
    var database: FirebaseDatabase? =null
    val fragmentTransaction:FragmentTransaction? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.homeframeLayout,ProfileMenu())
                fragmentTransaction?.commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_profile_menu, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/")
        databaseReference = database?.reference!!.child("UserInfo")
        val currentuser = auth.currentUser
        val fragmentTransaction = fragmentManager?.beginTransaction()

        if(currentuser == null){
            fragmentTransaction?.replace(R.id.homeframeLayout,Login())
            fragmentTransaction?.remove(this)
            fragmentTransaction?.commit()
        }else{
            loadprofile(view)
        }
        view.logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            fragmentTransaction?.replace(R.id.homeframeLayout,Login())
            fragmentTransaction?.remove(this)
            fragmentTransaction?.commit()
        }
        return view
    }
    private fun loadprofile(view: View){
        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)
        userreference?.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                view.userName.text = snapshot.child("Name").value.toString()
                var formatter = SimpleDateFormat("dd/MM/yyyy")
                var date=formatter.parse(snapshot.child("Dob").value.toString())
                var dob = Calendar.getInstance()
                dob.time = date
                val dobYear = dob.get(Calendar.YEAR)
                val dobMonth = dob.get(Calendar.MONTH)
                val dobDay = dob.get(Calendar.DAY_OF_MONTH)
                var age = getAge(dobYear, dobMonth, dobDay)
                view.userAge.text = "Current Age : " + age+" ("+snapshot.child("Dob").value.toString()+")"
                view.userContact.text= "Contact     : "+snapshot.child("Contact").value.toString()
                view.userEmail.text= "Email       : "+snapshot.child("Email").value.toString()


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun getAge(year: Int, month: Int, day: Int): String {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob[year, month] = day
        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }
        val ageInt = age
        return ageInt.toString()
    }
}