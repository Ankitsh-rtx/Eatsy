package com.example.eatsy.services
import com.example.eatsy.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SnapshotMetadata

class OrderService: Service() {
    val CHANNEL_ID="233"
    private  lateinit var notificationManager:NotificationManager
    private lateinit var firebaseDB:FirebaseFirestore
    private  lateinit var listener : ListenerRegistration
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        firebaseDB  = FirebaseFirestore.getInstance()
        notificationManager=getSystemService(NOTIFICATION_SERVICE)  as NotificationManager

    }
    override fun onBind(p0: Intent?): IBinder? {
       return  null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val channel =NotificationChannel(CHANNEL_ID,"order",NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        val orderid= intent?.getStringExtra("ORDER_ID")
        Log.d("noti",orderid.toString())

        listener=firebaseDB.collection("orders").document(orderid.toString()).addSnapshotListener{
            item,error->
                if(error!=null) return@addSnapshotListener
            val status =item?.get("status",Int::class.java)
            if(status==0) createNotification("Order Placed")
            else if(status==1) createNotification("Out for Delivery")
            else if(status==2) {
                createNotification("Deliverd")
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    override fun stopService(name: Intent?): Boolean {
        return super.stopService(name)
        listener.remove()
    }

    override fun onDestroy() {
        listener.remove()
        super.onDestroy()
    }
    fun createNotification(notificationText:String){

        val notification:Notification =NotificationCompat.Builder(this,CHANNEL_ID).setContentTitle("Order")
            .setContentText(notificationText).setPriority(NotificationCompat.PRIORITY_HIGH).setSmallIcon(R.drawable.ic_notification).setColor(resources.getColor(R.color.orange_500)).setDefaults(Notification.DEFAULT_ALL).build()
        Log.d("noti", notification.toString())
        notificationManager.notify(0,notification)
    }

}