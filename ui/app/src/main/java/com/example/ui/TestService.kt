package com.example.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber


class TestService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Main)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Timber.e("[onCreate] Service")
        val manager = getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "1",
                "test",
                NotificationManager.IMPORTANCE_NONE
            )
            manager.createNotificationChannel(serviceChannel)
        }

        val builder = NotificationCompat.Builder(this@TestService, "1")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.DEFAULT_LIGHTS)
            // Set the intent that will fire when the user taps the notification
//            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        serviceScope.launch {
            (1..1000).asFlow()
                .map {
                    delay(50)
                    it
                }.buffer(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .onEach {
                    delay(1500)
                    builder.setContentText("$it")

                    manager.notify(1, builder.build())
                }.launchIn(serviceScope)
        }

        startForeground(1, builder.build())
    }
}