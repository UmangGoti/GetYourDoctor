package com.example.getyourdoctor

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import java.text.SimpleDateFormat
import java.util.*

class SignUp : Fragment() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? =null
    var database: FirebaseDatabase? =null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        textview_date = view.dobEt
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/")
        databaseReference = database?.reference!!.child("UserInfo")
        val fragmentTransaction = fragmentManager?.beginTransaction()

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView(view)
            }
        }
        view.dobBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                var datePickerDialog = context?.let {
                    DatePickerDialog(
                        it,
                        dateSetListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH))
                }
                datePickerDialog?.datePicker?.maxDate = System.currentTimeMillis()
                datePickerDialog?.show()

            }

        })
        view.registerBtn.setOnClickListener {
            if (emailEt.text.toString().isNullOrEmpty() || passwordEt.text.toString().isNullOrEmpty()) {
                Toast.makeText(context,"Email Address or Password is not provided", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(
                    emailEt.text.toString(),
                    passwordEt.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val currentUser = auth.currentUser
                            val currentUserDb=databaseReference?.child((currentUser?.uid!!))
                            currentUserDb?.child("Name")?.setValue(userNameEt.text.toString())
                            currentUserDb?.child("Contact")?.setValue(contactEt.text.toString())
                            currentUserDb?.child("Dob")?.setValue(dobEt.text.toString())
                            currentUserDb?.child("Email")?.setValue(emailEt.text.toString())
                            Toast.makeText(context,"Sign Up successful", Toast.LENGTH_SHORT).show()

                        } else {
                            Toast.makeText(context,task.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        view.loginTv.setOnClickListener {
            fragmentTransaction?.replace(R.id.homeframeLayout,ProfileMenu())
            fragmentTransaction?.remove(this)
            fragmentTransaction?.commit()
        }
        return view
    }
    private fun updateDateInView(view: View) {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.text = sdf.format(cal.getTime())
    }
}