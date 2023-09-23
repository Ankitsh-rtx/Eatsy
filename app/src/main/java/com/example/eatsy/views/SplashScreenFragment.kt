package com.example.eatsy.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eatsy.R
import com.example.eatsy.databinding.FragmentSplashScreenBinding
import com.google.firebase.auth.FirebaseAuth


class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        if(firebaseAuth.currentUser==null){
            binding.loadingSplashScreen.visibility = View.GONE
            binding.loginSplashScreen.visibility = View.VISIBLE
        }

        binding.startBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_signup,SignupFragment()).addToBackStack(null).commit()
        }

        return binding.root
    }


}