package com.example.getyourdoctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_hospital_info.*

class HospitalInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_info)
        val id = intent.getStringExtra("HospId1")
        val name = intent.getStringExtra("HospName")
        val address = intent.getStringExtra("HospAddress")
        val information = intent.getStringExtra("HospInformation")
        val time = intent.getStringExtra("HospTime")
        val number = intent.getStringExtra("HospContact")
        val status = intent.getStringExtra("HospStatus")
        val hname = intent.getStringExtra("HospName1")
        val latitude:Double = intent.getDoubleExtra("HospLat1",21.22)
        val longitude:Double = intent.getDoubleExtra("HospLong1",73.22)
        hpName.text = name
        hpName.setSingleLine()
        hpName.isSelected = true

        hpAddress.text = address

        hpInformation.text = information

        hpTime.text = time

        hpNumber.text = number
        Toast.makeText(applicationContext, "ID "+id,Toast.LENGTH_LONG).show()
            //floatingActionButton.visibility = View.VISIBLE
            floatingActionButton.setOnClickListener {
                val intent = Intent(this, Appointment::class.java)
                intent.putExtra("HospId2", id)
                startActivity(intent)

            }
        linearL4.setOnClickListener {
            var intent = Intent(applicationContext, HospitalLocationActivity::class.java)
            intent.putExtra("HospName2",hname)
            intent.putExtra("HospLat2", latitude)
            intent.putExtra("HospLong2", longitude)

            startActivity(intent)

        }
    }
}