package com.example.eatsy.views
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.arch.core.executor.DefaultTaskExecutor
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.adapter.AddressViewAdapter
import com.example.eatsy.adapter.CartViewAdapter
import com.example.eatsy.adapter.MenuListAdapter
import com.example.eatsy.databinding.ActivityMainBinding
import com.example.eatsy.databinding.FragmentCartBinding
import com.example.eatsy.databinding.FragmentPaymentBinding
import com.example.eatsy.model.Address
import com.example.eatsy.model.CartItem
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import java.lang.reflect.Array


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartItemList:HashMap<String,CartItem>
    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = ContextCompat.getColor(
            requireContext(),
            R.color.white
        )
        requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar).visibility=View.VISIBLE

        val cartList = DataSource.orderList.second
        var cartListHM = this.arguments?.getSerializable("cartItems")

        cartItemList = if(cartListHM!=null){
            cartListHM as HashMap<String, CartItem>


        } else cartList

        if(cartItemList.size==0) {
            binding.emptyCart.visibility=View.VISIBLE
            binding.cartLayout.visibility=View.GONE
        }
        else binding.restaurantId.text = DataSource.orderList.first?.name.toString()

        binding.cartItemsRecyclerview.adapter = CartViewAdapter(context,cartItemList,binding)

        binding.cartItemsRecyclerview.layoutManager = LinearLayoutManager(context)

        // Specify fixed size to improve performance
        binding.cartItemsRecyclerview.setHasFixedSize(false)
        binding.cartItemsRecyclerview.isNestedScrollingEnabled = false


        //unhide navigation
        requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar).performShow()
        val navBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.visibility = View.GONE

        //payment fragment

        binding.proceedToPayTV.setOnClickListener{

            if(DataSource.user?.name=="") {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView,Add_basic_detail_fragment())?.addToBackStack(R.id.homeFragment.toString())
                    ?.commit()
                return@setOnClickListener
            }
            if(DataSource.user?.address==null) {
                binding.selectAddressBtn.performClick()
                return@setOnClickListener
            }
            val bundle=Bundle()
            bundle.putString("Final Amount",(totalPrice()-70+80).toString())
            val paymentFragment=PaymentFragment()
            paymentFragment.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView,paymentFragment)?.addToBackStack(R.id.homeFragment.toString())
                ?.commit()
        }

        binding.goToMenu.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, DiscoverFragment())?.addToBackStack(R.id.homeFragment.toString())
                ?.commit()
        }
        binding.backStackBtn.setOnClickListener {
            //need to press twice to get back to previous fragment due to null added to backstack
            activity?.supportFragmentManager?.popBackStackImmediate()
        }

        binding.selectAddressBtn.setOnClickListener {
            Log.d("CartFragment", "onSelectAddressClick:  ${requireContext()}")
            val dialogBottomSheetDialog = BottomSheetDialog(requireContext())
            val bottomSheetDialog =
                LayoutInflater.from(context).inflate(R.layout.bottom_address_dialog, null)
            dialogBottomSheetDialog.setContentView(bottomSheetDialog)
            val adapter = context?.let {AddressViewAdapter(it) }!!
            dialogBottomSheetDialog.findViewById<RecyclerView>(R.id.recycler_view)?.layoutManager=LinearLayoutManager(context)
            dialogBottomSheetDialog.findViewById<RecyclerView>(R.id.recycler_view)?.adapter=adapter
            dialogBottomSheetDialog.show()



            val addAddress = dialogBottomSheetDialog.findViewById<ConstraintLayout>(R.id.addAddress)
            addAddress?.setOnClickListener {
                Log.d("Cart Fragment" , "address clicked")
                //link it to display
                val dialog = BottomSheetDialog(requireContext())
                dialog.setContentView(LayoutInflater.from(context).inflate(R.layout.new_address_dialog,null))
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val listAdapter:ArrayAdapter<CharSequence> =
                    ArrayAdapter.createFromResource(requireContext(),R.array.country_arrays,android.R.layout.simple_spinner_dropdown_item)
                listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                val CountrySpinner:Spinner?=dialog.findViewById<Spinner>(R.id.country)
                CountrySpinner?.adapter=listAdapter
                val listAdapterSattes:ArrayAdapter<CharSequence> =
                    ArrayAdapter.createFromResource(requireContext(),R.array.states_array,android.R.layout.simple_spinner_dropdown_item)
                listAdapterSattes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                val StateSpinner:Spinner?=dialog.findViewById<Spinner>(R.id.state)
                StateSpinner?.adapter=listAdapterSattes
                dialog.show()

                dialog.findViewById<TextView>(R.id.add_new_address_btn)?.setOnClickListener {
//                    val country = dialog.findViewById<EditText>(R.id.country)?.text.toString()
                    val country=dialog.findViewById<Spinner>(R.id.country)?.selectedItem.toString()
//                    val state = dialog.findViewById<EditText>(R.id.state)?.text.toString()
                    val state=dialog.findViewById<Spinner>(R.id.state)?.selectedItem.toString()
                    val city = dialog.findViewById<EditText>(R.id.city)?.text.toString()
                    val street = dialog.findViewById<EditText>(R.id.street)?.text.toString()
                    val pincode=  dialog.findViewById<EditText>(R.id.pincode)?.text.toString()
                    val landmark = dialog.findViewById<EditText>(R.id.landmark)?.text.toString()
                    val address = Address( landmark,city,country,Integer.parseInt(pincode),street,state)
                    DataSource.address?.add(address)
                    Log.d("cart fragment","address: $address")
                    dialogBottomSheetDialog.findViewById<RecyclerView>(R.id.recycler_view)?.adapter?.notifyDataSetChanged()
                    dialog.hide()
                }

            }
        }

        val total:Long=totalPrice()
        binding.totalPrice.text = "₹ "+total.toString()
        binding.payableAmount.text = "₹ "+ (total-70+80).toString()
        binding.finalAmount.text = "₹ "+ (total-70+80).toString()


        Log.d("cart","closed")
        val sharedPreferences=requireActivity().getSharedPreferences("CART", Context.MODE_PRIVATE).edit()
        sharedPreferences.putString("CART",DataSource.orderList.toString())
        sharedPreferences.commit()
        val j=Gson().toJson(cartList);
        val d=Gson().fromJson<Any>(j,Any::class.java)
        Log.d("cart",j.toString())
        Log.d("cart",DataSource.orderList.toString())
        Log.d("cart",d.toString())
        return binding.root
    }

    private fun totalPrice():Long{
        var totalPrice:Long = 0
        cartItemList.forEach { (key, value) -> totalPrice+= value.getItem().price
            ?.times(value.getItemQuantity()) ?: 0 }
        return totalPrice
    }
}