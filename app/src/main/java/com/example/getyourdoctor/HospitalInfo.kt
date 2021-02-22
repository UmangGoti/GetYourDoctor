package com.example.getyourdoctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hospital_info.*

class HospitalInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_info)
        val name = intent.getStringExtra("HospName")
        val address = intent.getStringExtra("HospAddress")
        val information = intent.getStringExtra("HospInformation")
        val time = intent.getStringExtra("HospTime")
        val number = intent.getStringExtra("HospContact")
        hpName.text = name
        hpName.setSingleLine()
        hpName.isSelected = true

        hpAddress.text = address

        hpInformation.text = information

        hpTime.text = time

        hpNumber.text = number
    }
}