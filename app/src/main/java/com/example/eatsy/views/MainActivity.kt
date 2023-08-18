package com.example.eatsy.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.eatsy.R
import com.example.eatsy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background = null


        binding.bottomNavigationView.setOnItemSelectedListener{
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.homeFragment -> HomeFragment().also { fragment = it }
                R.id.discoverFragment -> DiscoverFragment().also { fragment = it }
                R.id.cartFragment -> CartFragment().also {fragment = it}
                R.id.trackOrderFragment -> TrackOrderFragment().also { fragment = it }
                R.id.profileFragment -> ProfileFragment().also { fragment = it }
                else -> {}
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, it)
                    .commit()
            }

            true
        }

//        binding.fab.setOnClickListener {
//
//            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//
//            fragmentTransaction.replace(R.id.fragmentContainerView, CartFragment()).commit()
//
//        }

    }




}