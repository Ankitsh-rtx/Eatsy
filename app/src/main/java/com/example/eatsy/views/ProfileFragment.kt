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
import com.example.eatsy.model.Item
import com.example.eatsy.model.Order
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        val navBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
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
        binding.accountTabLayout.setOnClickListener{
            val view = binding.accountDetails.visibility
            if (view==View.VISIBLE) binding.accountDetails.visibility = View.GONE
            else binding.accountDetails.visibility = View.VISIBLE
        }
        binding.orderTab.setOnClickListener{val orders = db.collection("orders").whereEqualTo("userId",firebaseAuth.currentUser!!.uid)
            orders.get().addOnSuccessListener {
                it.forEach { orders ->
                    val res = orders.toObject(Order::class.java)
                    Log.d("ProfileFragment:","${res.restaurantId}-> ${res.totalPrice}")
                }
            }.addOnFailureListener {
                Log.d("ProfileFragment:", "$it ")
            }
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