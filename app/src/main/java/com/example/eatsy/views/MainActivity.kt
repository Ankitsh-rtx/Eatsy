package com.example.eatsy.views

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.eatsy.R
import com.example.eatsy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val TAG:String = "Main Activity"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background = null



        binding.bottomNavigationView.setOnItemSelectedListener{ it ->
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.homeFragment -> HomeFragment().also { fragment = it }
                R.id.discoverFragment -> DiscoverFragment().also { fragment = it }
                R.id.cartFragment -> CartFragment().also {fragment = it}
                R.id.trackOrderFragment -> TrackOrderFragment().also { fragment = it }
                R.id.profileFragment -> ProfileFragment().also { fragment = it }
                else -> {}
            }
//            if(it.itemId == R.id.cartFragment){
//                binding.bottomAppBar.visibility = View.GONE
//            }
//            else {
//                binding.bottomAppBar.visibility = View.VISIBLE
//            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, it).addToBackStack(null)
                        .commit()
                }
            true
        }



//        supportFragmentManager.addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener {
//            // If the stack decreases it means I clicked the back button
//            if (fragmentManager.backStackEntryCount) {
//                //check your position based on selected fragment and set it accordingly.
//                navigation.getMenu().getItem(your_pos).setChecked(true)
//            }
//        })

//        val restaurantViewModel:RestaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
//        restaurantViewModel.getCart().observe(this, Observer<List<CartItem>>(){
//            Log.d(TAG,"onChanged: "+ it.size)
//        })

//        binding.fab.setOnClickListener {
//
//            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//
//            fragmentTransaction.replace(R.id.fragmentContainerView, CartFragment()).commit()
//
//        }

    }

    // function to override large font size into normal font size
    override fun attachBaseContext(newBase: Context?) {

        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)

        super.attachBaseContext(newBase)
    }




}