package com.example.eatsy.views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.ActionMode
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.databinding.ActivityMainBinding
import com.example.eatsy.model.Restaurants
import com.example.eatsy.model.Address
import com.example.eatsy.model.CartItem
import com.example.eatsy.services.OrderService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    private val TAG: String = "Main Activity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background = null

        Log.d("user", DataSource.user.toString())
        DataSource.address = DataSource.user?.address
        if(DataSource.address!=null && DataSource.address?.size!=0) {
            DataSource.orderAddress=DataSource.address!!.get(0);
        }


        binding.bottomNavigationView.setOnItemSelectedListener { it ->
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.homeFragment -> HomeFragment().also { fragment = it }
                R.id.discoverFragment -> DiscoverFragment().also { fragment = it }
                R.id.cartFragment -> CartFragment().also { fragment = it }
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

        // shared preference
        readData()


//        val firebaseDB = FirebaseFirestore.getInstance()
//        for (i in DataSource.sampleRestaurantDataList) {
//            firebaseDB.collection("Items").document("${i.id}").set(i)
//        }
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

    }
    private fun readData() {
        val sharedPreferences = baseContext.getSharedPreferences("cart", Context.MODE_PRIVATE)
        val gson = Gson()
        try {
            val jsonRes = sharedPreferences.getString("res", null)
            val jsonItem = sharedPreferences.getString("cartList", null)

            if (jsonRes != null && jsonItem != null) {
                val typeRes = object : TypeToken<Restaurants?>() {}.type
                val typeList = object : TypeToken<HashMap<String, CartItem>>() {}.type

                val cartItemList: HashMap<String, CartItem> = gson.fromJson(jsonItem, typeList)
                val res: Restaurants = gson.fromJson(jsonRes, typeRes)
                DataSource.orderList = Pair(res, cartItemList)

                Log.d("CartFragment", "res = $res")
                Log.d("CartFragment", "cartlist = $cartItemList")
            } else {
                // Handle the case where data in shared preferences is null or empty.
                DataSource.orderList = Pair(null,HashMap())
            }
        } catch (e: Exception) {
            // Handle any exceptions that occur during JSON parsing.
            e.printStackTrace()
        }

    }

    // function to override large font size into normal font size
    override fun attachBaseContext(newBase: Context?) {

        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)

        super.attachBaseContext(newBase)
    }




}