package com.example.getyourdoctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hospital_info.linearL4
import kotlinx.android.synthetic.main.activity_medical_info.*

class MedicalInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_info)

        val name = intent.getStringExtra("MclName")
        val address = intent.getStringExtra("MclAddress")
        val time = intent.getStringExtra("MclTime")
        val number = intent.getStringExtra("MclContact")
        val hname = intent.getStringExtra("MclName1")
        val latitude:Double = intent.getDoubleExtra("MclLat1",21.22)
        val longitude:Double = intent.getDoubleExtra("MclLong1",73.22)
        mcName.text = name
        mcName.setSingleLine()
        mcName.isSelected = true

        mcAddress.text = address

        mcTime.text = time

        mcNumber.text = number
        linearL4.setOnClickListener {
            var intent = Intent(applicationContext, MedicalLocationActivity::class.java)
            intent.putExtra("MclName2",hname)
            intent.putExtra("MclLat2", latitude)
            intent.putExtra("MclLong2", longitude)
            startActivity(intent)
        }
    }
}