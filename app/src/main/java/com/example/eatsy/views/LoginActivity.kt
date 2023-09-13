package com.example.eatsy.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()

        supportFragmentManager.beginTransaction().replace(R.id.fragment_signup,SplashScreenFragment()).commit()

    }
    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null){
            var firebaseDB  = FirebaseFirestore.getInstance()
            firebaseDB.collection("users").document(firebaseAuth.currentUser!!.uid.toString()).get().addOnSuccessListener { data ->
                val UserProfile = data.toObject(User::class.java)
                DataSource.user = UserProfile
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }


    }
}