package com.example.eatsy.views

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val extras = intent.extras;
        if (extras != null) {
            val email = extras.getString("email")
            val phone = extras.getString("phone")
            val amount = extras.getString("amount")
            finalAmout=amount.toString()
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
        val Order= Order(
            DataSource!!.user!!.id.toString(),
            DataSource.orderList.first?.id.toString(),
            DataSource.orderList.second.values.toList(),
            Timestamp( Date().time),
            finalAmout.toInt(),
            0,
            DataSource.orderAddress,
            0,
            p0)
        Log.d("order",Order.toString())
        firebaseDB.collection("orders").add(Order).addOnSuccessListener { document ->
            intent.putExtra("ORDER_ID", document.id)
            // order list cleared on order success
            DataSource.orderList = Pair(null, HashMap<String, CartItem>())
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
}