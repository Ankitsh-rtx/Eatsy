package com.example.eatsy.views

import android.os.Bundle
import android.util.Log
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
import com.example.eatsy.databinding.FragmentRestaurantDetailsBinding
import com.example.eatsy.model.CartItem
import com.example.eatsy.model.Item
import com.example.eatsy.model.Restaurants
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantDetailsFragment : Fragment() {
    private lateinit var binding:FragmentRestaurantDetailsBinding
    private lateinit var cartItemList:HashMap<String, CartItem>
    private lateinit var adapter: MenuListAdapter
    private lateinit var firebaseDB: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRestaurantDetailsBinding.inflate(layoutInflater)

        val navBar = activity!!.findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.visibility = View.GONE

        val bundle = arguments
        val restaurants: Restaurants? = bundle?.getSerializable("res") as Restaurants?

        binding.restaurantNameTextview.text = restaurants?.name
        binding.restaurantType.text = restaurants?.type
        binding.restaurantRating.text = restaurants?.rating.toString()
        binding.restaurantLocation.text = restaurants?.address?.city

        val menu= mutableListOf<Item>()
        firebaseDB  = FirebaseFirestore.getInstance()

        adapter = context?.let { MenuListAdapter(it, menu,binding,restaurants) }!!
        binding.menuItemRecyclerview.adapter= adapter
        binding.menuItemRecyclerview.layoutManager = LinearLayoutManager(context)
        restaurants?.menus?.forEach { id ->
            val item=firebaseDB.collection("Items").document(id)
            item.get().addOnSuccessListener { data ->
                val it = data.toObject(Item::class.java)
                Log.d("firebase menu item", it.toString())
                if (it != null) {
                    menu.add(it)
                    (binding.menuItemRecyclerview.adapter)?.notifyDataSetChanged()
                }
            }

        }

        val veg = mutableListOf<Item>()
        val nonveg = mutableListOf<Item>()

        binding.switchVeg.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.switchNonveg.isChecked = false
                veg.clear()
                for(item in menu){
                    if(item.isVeg==true) veg.add(item)
                }
                adapter = context?.let{MenuListAdapter(it,veg,binding,restaurants)}!!
                binding.menuItemRecyclerview.adapter = adapter
            }
            else {
                adapter = context?.let{MenuListAdapter(it,menu,binding,restaurants)}!!
                binding.menuItemRecyclerview.adapter = adapter
            }
        }
        binding.switchNonveg.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.switchVeg.isChecked = false
                nonveg.clear()
                for(item in menu){
                    if(item.isVeg==false) nonveg.add(item)
                }
                adapter = context?.let{MenuListAdapter(it,nonveg,binding,restaurants)}!!
                binding.menuItemRecyclerview.adapter = adapter
            }
            else {
                adapter = context?.let{MenuListAdapter(it,menu,binding,restaurants)}!!
                binding.menuItemRecyclerview.adapter = adapter
            }
        }

        firebaseDB.collection("Items").whereEqualTo("restaurantId",restaurants?.id).addSnapshotListener{
                i, _ ->
            for(j in i!!.documentChanges) {
                when (j.type) {
                    DocumentChange.Type.MODIFIED -> {
                        try{
                            val newItem=j.document.toObject(Item::class.java)
                            for (it in menu) {
                                if (it.id == newItem.id) {
                                    menu[menu.indexOf(it)]=newItem
                                    if (cartItemList.contains(it.id)) cartItemList[it.id.toString()]=CartItem(newItem,cartItemList.getValue(it.id.toString()).getItemQuantity())
                                    break;
                                }
                            }
                            binding.menuItemRecyclerview.adapter?.notifyDataSetChanged()
                        } catch(e:Exception){
                            Log.d("firebase ","error in loading Modified Doc $e")
                        }
                    }

                    else -> {}
                }
            }
        }



        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = context?.let { ContextCompat.getColor(it, R.color.ash_white) }!!

        val navView = activity!!.findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        binding.goToCartDialog.setOnClickListener {
//            if(navBar.isScrolledUp || navBar.visibility!=View.VISIBLE){
//                navBar.hideOnScroll = false
//                navBar.visibility = View.VISIBLE
//            }

            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView,CartFragment())?.addToBackStack(null)
                ?.commit()
            navView.selectedItemId = R.id.cartFragment


        }

        binding.backBtn.setOnClickListener {
            if(navBar.visibility!=View.VISIBLE)
                navBar.visibility = View.VISIBLE
            navView.selectedItemId = R.id.homeFragment
            activity?.supportFragmentManager?.popBackStackImmediate()


        }

        // Specify fixed size to improve performance
        binding.menuItemRecyclerview.setHasFixedSize(true)
        binding.menuItemRecyclerview.isNestedScrollingEnabled = false
        val countArr = IntArray(DataSource.items.size) { i-> 1 }

        cartItemList= DataSource.orderList.second
        if(cartItemList.size!=0) {
            binding.goToCartDialog.visibility= View.VISIBLE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


}