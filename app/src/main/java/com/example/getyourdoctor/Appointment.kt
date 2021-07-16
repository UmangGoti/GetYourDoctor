package com.example.getyourdoctor

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_appointment.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern


class Appointment : AppCompatActivity(), PaymentResultListener {



    val TAG:String = Appointment::class.toString()
    var paymentStatus = String()
    var paymentId = String()
    var id1: String? = null
    var hname1: String? = null
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? =null
    var database: FirebaseDatabase? =null
    var cal = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    val current = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = ofPattern("yyyy-MM-dd HH:mm:ss")
    @RequiresApi(Build.VERSION_CODES.O)
    val formatted = current.format(formatter)
    var formate = SimpleDateFormat("yyyy-MM-dd",Locale.US)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        Checkout.preload(applicationContext)

        id1 = intent.getStringExtra("HospId2")
        hname1 = intent.getStringExtra("HospName2")
        adBtn.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date = formate.format(selectedDate.time)
                adAEt.setText(date.toString())
            },
                calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.show()

        }


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/")
        databaseReference = database?.reference!!.child("Transaction")

        var paybtn: Button = findViewById(R.id.payBtn)
        paybtn.setOnClickListener {
            val name: EditText = findViewById(R.id.userNameAEt)
            val phnnum: EditText = findViewById(R.id.contactAEt)
            val age: EditText = findViewById(R.id.ageAEt)
            val appointmentDate: EditText = findViewById(R.id.adAEt)
            Log.i(TAG, phnnum.text.toString().length.toString())

            if (name.text.toString().isEmpty()){
                name.setError("Name is required.")}
            else if (phnnum.text.toString().isEmpty()){
                phnnum.setError("Phone number is required.")}
            else if (phnnum.text.toString().length != 10){
                phnnum.setError("Enter 10 digits.")}
            else if (age.text.toString().isEmpty()){
                age.setError("Age is required.")
            }
            else if(appointmentDate.text.toString().isEmpty()) {
                appointmentDate.setError("Appointment Date  is required.")
            }
            else {
                val uid = FirebaseAuth.getInstance().currentUser!!.getUid()
                val currentUserDb=databaseReference?.child(paymentId)
                if(uid!=null){
                    startPayment(currentUserDb)
                }else{
                    Toast.makeText(applicationContext, "Do Login First", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun startPayment(currentUserDb: DatabaseReference?) {
        val activity: Activity = this
        val co = Checkout()
        try {
            val options = JSONObject()
            options.put("name", "Get Your Doctor")
            options.put("description", "Appointment Booking Charges")
            options.put("currency", "INR")
            options.put("amount", "100")
            options.put("send_sms_hash", true);

            val prefill = JSONObject()
            prefill.put("email", "test@razorpay.com")
            prefill.put("contact", "Number")

            options.put("prefill", prefill)
            co.open(activity, options)
        }catch (e: Exception){
            Toast.makeText(activity, "Error In Payment ", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentError(errorCode: Int, response: String?) {
        try{
            Toast.makeText(this, "Payment Failed", Toast.LENGTH_LONG).show()
            val paymentI = JSONObject(response!!).getJSONObject("metadata")
            paymentId = paymentI.getString("payment_id")
            Log.i(TAG, paymentId)
        }catch (e: Exception){
            Log.e(TAG, "Exception in onPaymentSuccess", e)
            paymentStatus = "Failed"
        }
        val uid = FirebaseAuth.getInstance().currentUser!!.getUid()
        val currentUserDb=databaseReference?.push()

        currentUserDb?.child("tName")?.setValue(userNameAEt.text.toString())
        currentUserDb?.child("tContact")?.setValue(contactAEt.text.toString())
        currentUserDb?.child("tAge")?.setValue(ageAEt.text.toString())
        currentUserDb?.child("tBookingDate")?.setValue(formatted)
        currentUserDb?.child("tAppointmentDate")?.setValue(adAEt.text.toString())
        currentUserDb?.child("tPaymentStatus")?.setValue(paymentStatus)
        currentUserDb?.child("tUserID")?.setValue(uid)
        currentUserDb?.child("tHospitalID")?.setValue(id1)
        currentUserDb?.child("tHospitalName")?.setValue(hname1)
        var fragmentTransaction = supportFragmentManager?.beginTransaction().replace(R.id.homeframeLayout,CityHospitalListMenu())
        fragmentTransaction?.commit()
        finish()
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        try{
            Toast.makeText(this, "Appointment Booked successfully", Toast.LENGTH_LONG).show()
            if (razorpayPaymentId != null) {
                paymentId = razorpayPaymentId
            }
            paymentStatus = "Authorized"
        }catch (e: Exception) {
            Log.e(TAG, "Exception in onPaymentSuccess", e)
        }
        val uid = FirebaseAuth.getInstance().currentUser!!.getUid()
        val currentUserDb=databaseReference?.child(paymentId)

        currentUserDb?.child("tName")?.setValue(userNameAEt.text.toString())
        currentUserDb?.child("tContact")?.setValue(contactAEt.text.toString())
        currentUserDb?.child("tAge")?.setValue(ageAEt.text.toString())
        currentUserDb?.child("tBookingDate")?.setValue(formatted)
        currentUserDb?.child("tAppointmentDate")?.setValue(adAEt.text.toString())
        currentUserDb?.child("tPaymentStatus")?.setValue(paymentStatus)
        currentUserDb?.child("tUserID")?.setValue(uid)
        currentUserDb?.child("tHospitalID")?.setValue(id1)
        currentUserDb?.child("tHospitalName")?.setValue(hname1)
        startActivity(Intent(this@Appointment,UserTransectionActivity::class.java))
        finish()
    }



}

