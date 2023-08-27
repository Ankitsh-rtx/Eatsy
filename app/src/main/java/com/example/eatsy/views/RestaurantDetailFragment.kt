package com.example.eatsy.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.MenuListAdapter
import com.example.eatsy.databinding.FragmentRestaurantDetailBinding
import com.example.eatsy.model.Item


class RestaurantDetailFragment : Fragment() {
//   private lateinit var binding: FragmentRestaurantDetailBinding
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentRestaurantDetailBinding.inflate(layoutInflater)
//
//        val name = arguments!!.getString("restaurant_name")
//        val type = arguments!!.getString("restaurant_type")
//        val rating = arguments!!.getString("restaurant_type")
//        val address = arguments!!.getString("restaurant_address")
//        val menu = arguments!!.getSerializable("restaurant_menu") as ArrayList<Item>
//
//        binding.restaurantNameTextview.text = name
//        binding.restaurantType.text = type
//        binding.restaurantRating.text = rating
//        binding.restaurantLocation.text = address
//
//        // Status bar color
//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.ash_white)
//
//        val adapter = MenuListAdapter(requireContext(), menu, binding)
//        binding.menuItemRecyclerview.adapter= adapter
//        binding.menuItemRecyclerview.layoutManager = LinearLayoutManager(requireContext())
//
//        binding.goToCartDialog.setOnClickListener {
//            val fragment: Fragment = CartFragment()
//            val fragmentManager = activity!!.supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.restaurantDetailFragment, fragment)
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
//
////            val i = Intent(applicationContext, MainActivity::class.java)
////            i.putExtra("cart", "cart")
////            startActivity(i)
//        }
//        // Specify fixed size to improve performance
//        binding.menuItemRecyclerview.setHasFixedSize(true)
//        binding.menuItemRecyclerview.isNestedScrollingEnabled = false
//        val countArr = IntArray(DataSource.items.size) { i-> 1 }
//
//        val cartItemList= DataSource.orderList
//        if(cartItemList.size!=0) {
//            binding.goToCartDialog.visibility= View.VISIBLE
//        }
//
//        return binding.root
//    }


}