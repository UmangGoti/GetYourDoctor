package com.example.getyourdoctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
        bottomNavigation.selectedItemId = R.id.hospitalsEnabled
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.hospitalsEnabled -> {
                supportFragmentManager.beginTransaction().replace(R.id.homeframeLayout,CityHospitalListMenu()).commit()
                return true
            }
            R.id.medicalsDisabled -> {
                supportFragmentManager.beginTransaction().replace(R.id.homeframeLayout,MedicalListMenu()).commit()
                return true
            }
            R.id.profileDisabled -> {
                supportFragmentManager.beginTransaction().replace(R.id.homeframeLayout,ProfileMenu()).commit()
                return true
            }
        }
        return false
    }
}