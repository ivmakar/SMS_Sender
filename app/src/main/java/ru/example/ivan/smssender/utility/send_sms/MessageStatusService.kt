package ru.example.ivan.smssender.utility.send_sms

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import ru.example.ivan.smssender.data.repositories.MessageRepository
import ru.example.ivan.smssender.utility.Constants
import javax.inject.Inject

class MessageStatusService: Service() {

    @Inject
    lateinit var messageStatusReceiver: MessageStatusReceiver

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("MyReceiver", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val res = super.onStartCommand(intent, flags, startId)

        Log.d("MyReceiver", "onStartCommand")

        applicationContext.registerReceiver(
            messageStatusReceiver,
            IntentFilter(Constants.SMS_SENT_INTENT).also { it.addAction(
            Constants.SMS_DELIVERED_INTENT) })

        return res
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyReceiver", "onDestroy")
    }
}