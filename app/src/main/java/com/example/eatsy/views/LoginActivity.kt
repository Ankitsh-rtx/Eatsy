package com.example.eatsy.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eatsy.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_signup,SplashScreenFragment()).commit()

    }
}