package com.example.getyourdoctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class HospitalLocationActivity : AppCompatActivity(),  OnMapReadyCallback  {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_location)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        var latitude: Double = intent.getDoubleExtra("HospLat", 21.22)
        var longitude: Double = intent.getDoubleExtra("HospLong", 73.22)
        val destination = LatLng(latitude, longitude)
        var name:String = intent.getStringExtra("HospName")
        val markerDestination = MarkerOptions()
            .position(destination)
            .title(name)
        mMap.addMarker(markerDestination)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destination, 14f))

    }
}