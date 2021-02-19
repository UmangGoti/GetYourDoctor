package com.example.getyourdoctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hospital_info.*
import kotlinx.android.synthetic.main.activity_hospital_info.hName
import kotlinx.android.synthetic.main.hospital_info.*

class HospitalInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_info)
        val name = intent.getStringExtra("HospName")
        val address = intent.getStringExtra("HospAddress")
        val information = intent.getStringExtra("HospInformation")

        hName.text = name
        hName.setSingleLine()
        hName.isSelected = true

        hAddress.text = address

        hInformation.text = information
    }
}