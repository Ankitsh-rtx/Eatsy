package com.example.eatsy.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.databinding.ActivityMainBinding
import com.example.eatsy.databinding.FragmentProfileBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        val navBar = activity!!.findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.visibility = View.GONE

        binding.backBtn.setOnClickListener{
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        if(DataSource.user?.name?.isNotEmpty() == true){
            binding.username.text = DataSource.user!!.name
        }
        else {
            Log.d("Profile Fragment","${DataSource.user?.id}")
            val id = DataSource.user!!.id
            binding.username.text = "User"+id?.substring(0, id?.length?.minus(24) ?: 0)
        }
        binding.userEmail.text = DataSource.user?.email
        binding.userPhone.text = DataSource.user?.phone

        binding.editProfileBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.fragmentContainerView,Add_basic_detail_fragment()).commit()
        }

        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return binding.root
    }


}