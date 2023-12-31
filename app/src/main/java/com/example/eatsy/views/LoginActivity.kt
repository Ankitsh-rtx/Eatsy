package com.example.eatsy.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
            val firebaseDB  = FirebaseFirestore.getInstance()
            firebaseDB.collection("users").document(firebaseAuth.currentUser!!.uid.toString()).get().addOnSuccessListener { data ->
                val userProfile = data.toObject(User::class.java)
                DataSource.user = userProfile
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }
}