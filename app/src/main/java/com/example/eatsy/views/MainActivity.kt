package com.example.eatsy.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
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
//            if(it.itemId == R.id.cartFragment){
//                binding.bottomAppBar.visibility = View.GONE
//            }
//            else {
//                binding.bottomAppBar.visibility = View.VISIBLE
//            }
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, it)
                    .commit()
            }

            true
        }

        if(intent.getStringExtra("cart").equals("cart")) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,CartFragment()).commit()
            binding.bottomNavigationView.selectedItemId=R.id.cartFragment
        }
        else if(intent.getStringExtra("discover").equals("discover")) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,DiscoverFragment()).commit()
            binding.bottomNavigationView.selectedItemId=R.id.discoverFragment
        }

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




}