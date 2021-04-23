package com.example.getyourdoctor

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Appointment : AppCompatActivity(), PaymentResultListener {



    val TAG:String = Appointment::class.toString()
    var paymentStatus = String()
    var paymentId = String()
    var id1: String? = null
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? =null
    var database: FirebaseDatabase? =null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.O)
    val current = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    @RequiresApi(Build.VERSION_CODES.O)
    val formatted = current.format(formatter)
    var formate = SimpleDateFormat("yyyy-MM-dd",Locale.US)
    var timeFormat = SimpleDateFormat("HH:mm:ss", Locale.US)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        Checkout.preload(applicationContext)

        id1 = intent.getStringExtra("HospId2")
        adBtn.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                val date = formate.format(selectedDate.time)
                adAEt.setText(date.toString())
                Toast.makeText(this,"date : " + date,Toast.LENGTH_SHORT).show()
            },
                calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.show()

        }






        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://getyourdoctor-acf57-default-rtdb.firebaseio.com/")
        databaseReference = database?.reference!!.child("Transaction")

        var paybtn: Button = findViewById(R.id.payBtn)
        paybtn.setOnClickListener {
            val name : EditText = findViewById(R.id.userNameAEt)
            val phnnum : EditText = findViewById(R.id.contactAEt)
            val age : EditText = findViewById(R.id.ageAEt)
            val appointmentDate : EditText = findViewById(R.id.adAEt)
            Log.i(TAG, phnnum.text.toString().length.toString())

            if (name.text.toString().isEmpty())
                name.setError("Name is required.")


            else if(phnnum.text.toString().isEmpty() )
                phnnum.setError("Phone number is required.")

            else if(phnnum.text.toString().length != 10)
                phnnum.setError("Enter 10 digits.")

            else if(age.text.toString().isEmpty())
                age.setError("Age is required.")

            

            else if(appointmentDate.text.toString().isEmpty())
                appointmentDate.setError("Appointment Date  is required.")

            else
                startPayment()

        }

    }


    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()
        val currentUser = auth.currentUser
        val uid = FirebaseAuth.getInstance().currentUser!!.getUid()
        val currentUserDb=databaseReference?.child(paymentId)

        try {
            val options = JSONObject()
            options.put("name", "Get Your Doctor")
            options.put("description", "Appointment Booking Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR")
            options.put("amount", "100")
            options.put("send_sms_hash", true);

            val prefill = JSONObject()
            prefill.put("email", "test@razorpay.com")
            prefill.put("contact", "8155837716")

            options.put("prefill", prefill)
            co.open(activity, options)
        }catch (e: Exception){
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentError(errorCode: Int, response: String?) {
        try{
            Toast.makeText(this, "Payment failed $errorCode \n $response", Toast.LENGTH_LONG).show()

            val paymentI = JSONObject(response!!).getJSONObject("metadata")
            paymentId = paymentI.getString("payment_id")
            Log.i(TAG, paymentId)
        }catch (e: Exception){
            Log.e(TAG, "Exception in onPaymentSuccess", e)
            paymentStatus = "Failed"
        }
        val currentUser = auth.currentUser
        val uid = FirebaseAuth.getInstance().currentUser!!.getUid()
        val currentUserDb=databaseReference?.child(paymentId)

        currentUserDb?.child("Name")?.setValue(userNameAEt.text.toString())
        currentUserDb?.child("Contact")?.setValue(contactAEt.text.toString())
        currentUserDb?.child("Age")?.setValue(ageAEt.text.toString())
        currentUserDb?.child("BookingDate")?.setValue(formatted)
        currentUserDb?.child("AppointmentDate")?.setValue(adAEt.text.toString())
        currentUserDb?.child("Payment Status")?.setValue(paymentStatus)
        currentUserDb?.child("UserID")?.setValue(uid)
        currentUserDb?.child("HospitalID")?.setValue(id1)
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        try{
            Toast.makeText(this, "Payment Successful $razorpayPaymentId", Toast.LENGTH_LONG).show()
            Toast.makeText(this, "Appointment Booked successfully", Toast.LENGTH_LONG).show()
            if (razorpayPaymentId != null) {
                paymentId = razorpayPaymentId
            }
            paymentStatus = "Authorized"
            Toast.makeText(this, paymentId, Toast.LENGTH_LONG).show()



        }catch (e: Exception) {
            Log.e(TAG, "Exception in onPaymentSuccess", e)
        }

        val currentUser = auth.currentUser
        val uid = FirebaseAuth.getInstance().currentUser!!.getUid()
        val currentUserDb=databaseReference?.child(paymentId)

        currentUserDb?.child("Name")?.setValue(userNameAEt.text.toString())
        currentUserDb?.child("Contact")?.setValue(contactAEt.text.toString())
        currentUserDb?.child("Age")?.setValue(ageAEt.text.toString())
        currentUserDb?.child("BookingDate")?.setValue(formatted)
        currentUserDb?.child("AppointmentDate")?.setValue(adAEt.text.toString())
        currentUserDb?.child("Payment Status")?.setValue(paymentStatus)
        currentUserDb?.child("UserID")?.setValue(uid)
        currentUserDb?.child("HospitalID")?.setValue(id1)
    }



}

