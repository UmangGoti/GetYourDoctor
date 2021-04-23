package com.example.getyourdoctor


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*import kotlinx.android.synthetic.main.activity_hospital_info.*
import kotlinx.android.synthetic.main.fragment_profile_menu.*
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
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                System.exit(0)
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
            fragmentTransaction?.replace(R.id.homeframeLayout, Login())
            fragmentTransaction?.remove(this)
            fragmentTransaction?.commit()
        }else{
            loadprofile(view)
        }

        view.logoutIcon.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            fragmentTransaction?.replace(R.id.homeframeLayout, Login())
            fragmentTransaction?.remove(this)
            fragmentTransaction?.commit()
        }

        view.editIcon.setOnClickListener {
            EditInformation(view)
        }
        view.setOnClickListener {
            startActivity(Intent(context,UserTransectionActivity::class.java))
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
                view.userName.setSingleLine()
                view.userName.isSelected = true

                var formatter = SimpleDateFormat("dd/MM/yyyy")
                var date = formatter.parse(snapshot.child("Dob").value.toString())
                var dob = Calendar.getInstance()
                dob.time = date
                val dobYear = dob.get(Calendar.YEAR)
                val dobMonth = dob.get(Calendar.MONTH)
                val dobDay = dob.get(Calendar.DAY_OF_MONTH)
                var age = getAge(dobYear, dobMonth, dobDay)
                view.userAge.text = age + " (" + snapshot.child("Dob").value.toString() + ")"

                view.userContact.text = snapshot.child("Contact").value.toString()

                view.userEmail.text = snapshot.child("Email").value.toString()
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

    private fun EditInformation(view: View){
        view.userName.visibility = View.GONE
        view.editIcon.visibility =View.GONE
        view.ageIcon.visibility = View.GONE
        view.userAge.visibility = View.GONE
        view.callIcon.visibility = View.GONE
        view.userContact.visibility = View.GONE
        view.emailIcon.visibility = View.GONE
        view.userEmail.visibility = View.GONE
        view.transectionBtn.visibility = View.GONE
        view.view1.visibility = View.GONE
        view.view2.visibility = View.GONE
        view.view3.visibility = View.GONE
        view.view4.visibility = View.GONE
        view.view5.visibility = View.GONE

        view.userNameIcon.visibility = View.VISIBLE
        view.userNameEt.visibility = View.VISIBLE
        view.userDobIcon.visibility = View.VISIBLE
        view.userDobEt.visibility = View.VISIBLE
        view.userCallIcon.visibility = View.VISIBLE
        view.userContactEt.visibility = View.VISIBLE
        view.updateBtn.visibility = View.VISIBLE

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)
        userreference?.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                view.userNameEt.setText(snapshot.child("Name").value.toString())
                view.userDobEt.setText(snapshot.child("Dob").value.toString())
                view.userContactEt.setText(snapshot.child("Contact").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        view.updateBtn.setOnClickListener {
            val user = auth.currentUser
            val userreference = databaseReference?.child(user?.uid!!)
            var flag:Int = 0
            userreference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val key = snapshot.key
                    if (key != null && (view.userNameEt.text.toString()).isNotEmpty() && (view.userContactEt.text.toString()).isNotEmpty() && (view.userDobEt.text.toString()).isNotEmpty()) {
                        userreference.child("Name").setValue(view.userNameEt.text.toString())
                        userreference.child("Dob").setValue(view.userDobEt.text.toString())
                        userreference.child("Contact").setValue(view.userContactEt.text.toString())
                        flag = 1
                    } else {
                        flag = 0
                        Toast.makeText(context, "Enter Valid Value", Toast.LENGTH_SHORT).show()
                    }

                    if (flag == 1) {
                        view.userNameIcon.visibility = View.GONE
                        view.userNameEt.visibility = View.GONE
                        view.userDobIcon.visibility = View.GONE
                        view.userDobEt.visibility = View.GONE
                        view.userCallIcon.visibility = View.GONE
                        view.userContactEt.visibility = View.GONE
                        view.updateBtn.visibility = View.GONE


                        view.userName.visibility = View.VISIBLE
                        view.editIcon.visibility = View.VISIBLE
                        view.ageIcon.visibility = View.VISIBLE
                        view.userAge.visibility = View.VISIBLE
                        view.callIcon.visibility = View.VISIBLE
                        view.userContact.visibility = View.VISIBLE
                        view.emailIcon.visibility = View.VISIBLE
                        view.userEmail.visibility = View.VISIBLE
                        view.transectionBtn.visibility = View.VISIBLE
                        view.view1.visibility = View.VISIBLE
                        view.view2.visibility = View.VISIBLE
                        view.view3.visibility = View.VISIBLE
                        view.view4.visibility = View.VISIBLE
                        view.view5.visibility = View.VISIBLE
                    } else {
                        view.userName.visibility = View.GONE
                        view.editIcon.visibility = View.GONE
                        view.ageIcon.visibility = View.GONE
                        view.userAge.visibility = View.GONE
                        view.callIcon.visibility = View.GONE
                        view.userContact.visibility = View.GONE
                        view.emailIcon.visibility = View.GONE
                        view.userEmail.visibility = View.GONE
                        view.transectionBtn.visibility = View.GONE
                        view.view1.visibility = View.GONE
                        view.view2.visibility = View.GONE
                        view.view3.visibility = View.GONE
                        view.view4.visibility = View.GONE
                        view.view5.visibility = View.GONE


                        view.userNameIcon.visibility = View.VISIBLE
                        view.userNameEt.visibility = View.VISIBLE
                        view.userDobIcon.visibility = View.VISIBLE
                        view.userDobEt.visibility = View.VISIBLE
                        view.userCallIcon.visibility = View.VISIBLE
                        view.userContactEt.visibility = View.VISIBLE
                        view.updateBtn.visibility = View.VISIBLE
                    }

                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}