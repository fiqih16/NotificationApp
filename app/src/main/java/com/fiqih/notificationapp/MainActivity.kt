package com.fiqih.notificationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    private lateinit var notificationManager: NotificationManagerCompat

    companion object{
        const val BUTTON1 = "btn1"
        const val BUTTON2 = "btn2"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = NotificationManagerCompat.from(this)

        btn_et.setOnClickListener {
            val title = JudulEt.text.toString()
            val message = IsiEt.text.toString()

            val etUrl = UrlEt.text.toString()
            var url = URLEncoder.encode(etUrl)
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://$url"
            }
            Log.d("urlTag", url)

            val resultIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(this,BUTTON1)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSubText("Pesan Masuk")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)

                .addAction(R.mipmap.ic_launcher,"Open URL",pendingIntent)
                .setOnlyAlertOnce(true)
                .setColor(Color.GREEN)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notification = builder.build()
            notificationManager.notify(1,notification)

            JudulEt.text.clear()
            IsiEt.text.clear()
            UrlEt.text.clear()
        }

        btn_et2.setOnClickListener {
            val title = JudulEt.text.toString()
            val message = IsiEt.text.toString()

            val etUrl = UrlEt.text.toString()
            var url = URLEncoder.encode(etUrl)
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://$url"
            }
            Log.d("urlTag", url)

            val resultIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = NotificationCompat.Builder(this,BUTTON2)
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)

                .addAction(R.mipmap.ic_launcher,"Open URL",pendingIntent)
                .setOnlyAlertOnce(true)
                .setColor(Color.GREEN)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notification = builder.build()
            notificationManager.notify(2,notification)

            JudulEt.text.clear()
            IsiEt.text.clear()
            UrlEt.text.clear()
        }



        createNotificationChannel()
        btn_send.setOnClickListener {
            sendNotification()
        }
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notofication Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = descriptionText

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
            notificationManager.createNotificationChannel(channel)

            // Button Notif
            val btn1 = NotificationChannel(
                BUTTON1, "Channel Satu", NotificationManager.IMPORTANCE_HIGH
            )
            btn1.description = "Ini adalah channel 1"

            val btn2 = NotificationChannel(
                BUTTON2, "Channel Dua", NotificationManager.IMPORTANCE_LOW
            )
            btn1.description = "Ini adalah channel 2"

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(btn1)
            manager?.createNotificationChannel(btn2)
        }
    }

    private fun sendNotification() {
        val intent = Intent(this,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,intent,0)
        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.firebase)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.html)
        val builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Example Title")
            .setContentText("Example Description")
            .setLargeIcon(bitmapLargeIcon)

            .setStyle(NotificationCompat.BigTextStyle().bigText("HALLO FIQIH"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId,builder.build())
        }
    }

}