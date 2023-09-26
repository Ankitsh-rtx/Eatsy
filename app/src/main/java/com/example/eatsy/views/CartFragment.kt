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
import android.widget.Toast
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
import com.example.eatsy.model.Restaurants
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.razorpay.Checkout
import org.json.JSONObject

import java.lang.reflect.Array


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartItemList:HashMap<String,CartItem>
    private lateinit var res: Restaurants
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


        return binding.root
    }

//    private fun makePayment(email:String,contact:String, amount:Long) {
//        val co = Checkout()
//        try {
//            val options = JSONObject()
//            options.put("name","Eatsy")
//            options.put("description","Demoing Charges")
//            //You can omit the image option to fetch the image from the dashboard
//            options.put("image",R.mipmap.ic_launcher)
//            options.put("theme.color", "#3399cc");
//            options.put("currency","INR");
////            options.put("order_id", orderID);
//            options.put("amount",amount)//pass amount in currency subunits
//
//
//            val prefill = JSONObject()
//            prefill.put("email","")
//            prefill.put("contact","")
//
//            options.put("prefill",prefill)
//            co.open(activity,options)
//        }catch (e: Exception){
//            Toast.makeText(requireActivity(),"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
//            e.printStackTrace()
//        }
//    }

    private fun totalPrice():Long{
        var totalPrice:Long = 0
        cartItemList.forEach { (key, value) -> totalPrice+= value.getItem().price
            ?.times(value.getItemQuantity()) ?: 0 }
        return totalPrice
    }

    override fun onResume() {
        super.onResume()
        Log.d("hoja re baba", "onResume:i was called ")

        val cartList = DataSource.orderList.second
        Log.d("cart fragment","cartList = ${cartList.size}")
        val cartListHM = this.arguments?.getSerializable("cartItems")
        var v : HashMap<String,CartItem> = HashMap()
        if(cartListHM!=null) {
            v = cartListHM as HashMap<String, CartItem>
        }
        Log.d("cart fragment","cartListHm = ${(v).size}")

        cartItemList = if(cartListHM!=null){
            cartListHM as HashMap<String, CartItem>

        } else cartList


        if(cartItemList.size==0) {
            binding.emptyCart.visibility=View.VISIBLE
            binding.cartLayout.visibility=View.GONE
        }
        //-- updated code --
        else binding.restaurantId.text = DataSource.orderList.first?.name

        binding.cartItemsRecyclerview.adapter = CartViewAdapter(context,cartItemList,binding)

        binding.cartItemsRecyclerview.layoutManager = LinearLayoutManager(context)

        // Specify fixed size to improve performance
        binding.cartItemsRecyclerview.setHasFixedSize(false)
        binding.cartItemsRecyclerview.isNestedScrollingEnabled = false


        //unhide navigation
        requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar).performShow()
        val navBar = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBar)
        navBar.visibility = View.INVISIBLE

        //payment fragment
        binding.proceedToPayTV.setOnClickListener{
            // update code ---
            if(DataSource.orderList.first?.name == "") {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView,Add_basic_detail_fragment())?.addToBackStack(null)
                    ?.commit()
                return@setOnClickListener
            }
            if(DataSource.address ==null) {
                binding.selectAddressBtn.performClick()
                return@setOnClickListener
            }
            val email = DataSource.user?.email.toString()
            val phone = DataSource.user?.phone.toString()
            val amount = totalPrice().toString()

//            makePayment(email,phone,amount)
            val intent = Intent(requireActivity(),PaymentActivity::class.java)
            intent.putExtra("email",email)
                .putExtra("phone",phone)
                .putExtra("amount",amount)
            startActivity(intent)


//            val bundle=Bundle()
//            bundle.putString("Final Amount",(totalPrice()-70+80).toString())
//            val paymentFragment=PaymentFragment()
//            paymentFragment.arguments=bundle
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.fragmentContainerView,paymentFragment)?.addToBackStack(null)
//                ?.commit()
        }

        binding.goToMenu.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainerView, DiscoverFragment())?.addToBackStack(null)
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
            val adapter = context?.let {AddressViewAdapter(it,DataSource.address) }!!
            adapter.setOnClickListener(object:AddressViewAdapter.OnItemClickListener{
                override fun onClick(position: Int, address: Address) {
                    DataSource.orderAddress=address

                    dialogBottomSheetDialog.hide()
                }
            })
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
                    if(DataSource.address==null) DataSource.address= mutableListOf(address)
                    else DataSource.address?.add(address)
                    val firebasedb= FirebaseFirestore.getInstance()
                    firebasedb.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).update("address",DataSource.address).addOnSuccessListener {  }
                    DataSource.orderAddress=address
                    dialogBottomSheetDialog.hide()
                    Log.d("cart fragment",DataSource?.address.toString())
                    dialogBottomSheetDialog.findViewById<RecyclerView>(R.id.recycler_view)?.adapter?.notifyDataSetChanged()
                    dialog.hide()
                }

            }
        }

        val total:Long=totalPrice()
        binding.totalPrice.text = "₹ "+total.toString()
        binding.payableAmount.text = "₹ "+ (total-70+80).toString()
        binding.finalAmount.text = "₹ "+ (total-70+80).toString()

    }


}