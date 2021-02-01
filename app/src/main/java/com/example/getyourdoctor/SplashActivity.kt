package com.example.getyourdoctor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var splashanim:Animation = AnimationUtils.loadAnimation(this,R.anim.splash_anim)
        appIcon.startAnimation(splashanim)
        appName.startAnimation(splashanim)
        Handler().postDelayed(
            {
                val i = Intent(this,HomeActivity::class.java)
                startActivity(i)
                finish()
            },2000
        )
    }
}