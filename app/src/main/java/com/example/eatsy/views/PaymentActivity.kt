package com.example.eatsy.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eatsy.DataSource
import com.example.eatsy.R
import com.example.eatsy.model.Order
import com.example.eatsy.model.CartItem
import com.google.firebase.firestore.FirebaseFirestore
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.sql.Timestamp
import java.util.Date

class PaymentActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var finalAmout: String
    private lateinit var orderid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val extras = intent.extras;
        if (extras != null) {
            val email = extras.getString("email")
            val phone = extras.getString("phone")
            val amount = extras.getString("amount")
            finalAmout=amount.toString()
            orderid=extras.getString("ORDER_ID").toString()
            makePayment(email,phone,amount+"00")
        }


    }
    private fun makePayment(email:String?,phone:String?,amount:String?) {
        val co = Checkout()
        try {
            val options = JSONObject()
            options.put("name","Eatsy")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image",R.mipmap.ic_launcher)
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("amount",amount)
//            options.put("order_id", orderID);

            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            //pass amount in currency subunits
            val prefill = JSONObject()
            prefill.put("email",email)
            prefill.put("contact",phone)

            options.put("prefill",prefill)
            co.open(this,options)
        }catch (e: Exception){
            Toast.makeText(this,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("paymentSuccess",p0)
        val firebaseDB  = FirebaseFirestore.getInstance()
        firebaseDB.collection("orders").document(orderid).update("transactionId",p0,"status",0).addOnSuccessListener { document ->
            intent.putExtra("ORDER_ID", orderid)
            // order list cleared on order success
            DataSource.orderList = Pair(null, HashMap<String, CartItem>())
            clearData()
            startActivity(intent)
            finish()
        }
//        Toast.makeText(this,"Payment Successfully Done: $p0",Toast.LENGTH_SHORT).show()
    }


    override fun onPaymentError(p0: Int, p1: String?) {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("paymentFailed",p1)
        startActivity(intent)
        finish()
//        Toast.makeText(this,"Payment Failed: $p1",Toast.LENGTH_SHORT).show()
    }
    private fun clearData(){
        val sharedPreferences = this.getSharedPreferences("cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }
}