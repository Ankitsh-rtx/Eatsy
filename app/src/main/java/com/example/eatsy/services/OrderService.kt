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

class OrderService: Service() {
    val CHANNEL_ID="233"
    private  lateinit var notificationManager:NotificationManager
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        notificationManager=getSystemService(NOTIFICATION_SERVICE)  as NotificationManager

    }
    override fun onBind(p0: Intent?): IBinder? {
       return  null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val channel =NotificationChannel(CHANNEL_ID,"order",NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        createNotification()
        stopSelf()
        return START_NOT_STICKY
    }

    fun createNotification(){

        val notification:Notification =NotificationCompat.Builder(this,CHANNEL_ID).setContentTitle("Order")
            .setContentText("done Placed").setPriority(NotificationCompat.PRIORITY_HIGH).setSmallIcon(R.drawable.icon_arrow_down).setDefaults(Notification.DEFAULT_ALL).build()
        Log.d("noti", notification.toString())
        notificationManager.notify(0,notification)
    }

}