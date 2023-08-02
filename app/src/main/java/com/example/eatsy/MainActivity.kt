package com.example.eatsy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.eatsy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        binding.bottomNavigationView.setOnItemSelectedListener{
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.homeFragment -> HomeFragment().also { fragment = it }
                R.id.discoverFragment -> DiscoverFragment().also { fragment = it }
                R.id.trackOrderFragment -> TrackOrderFragment().also { fragment = it }
                R.id.profileFragment -> ProfileFragment().also { fragment = it }
                else -> {}
            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, it) // Replace "fragmentContainer" with your actual fragment container ID
                    .commit()
            }

            true
        }
    }


}