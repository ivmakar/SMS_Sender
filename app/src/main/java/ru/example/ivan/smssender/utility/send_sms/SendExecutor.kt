package ru.example.ivan.smssender.utility.send_sms

import android.content.Context
import android.telephony.SmsManager
import android.telephony.SubscriptionManager
import ru.example.ivan.smssender.data.dbmodels.Message
import ru.example.ivan.smssender.data.repositories.MessageRepository
import ru.example.ivan.smssender.utility.Constants
import javax.inject.Inject
import android.content.IntentFilter
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.app.PendingIntent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.data.dbmodels.MessageToUser


class SendExecutor @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters): Worker(appContext, workerParams) {

    private var messageToUserList = ArrayList<MessageToUser>()
    private val subscriptionManager
            = applicationContext.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

    private var sendingMessageCount = 0
    private var sentStatusMessageCount = 0
    private lateinit var message: Message

    private lateinit var notificationManager: NotificationManager
    private lateinit var notification: NotificationCompat.Builder


    lateinit var messageRepository: MessageRepository

    override fun doWork(): Result {
        val messageId = inputData.getLong(Constants.KEY_MESSAGE_ID, 0)
        val message = messageRepository.getMessageById(messageId)

        initSending(message)

        return Result.success()
    }

    fun initSending(msg: Message) {

        notifyStartMessaging()

        message = msg
        messageToUserList = messageRepository.getMessageToUserListByMessageId(message.id!!)

        for (i in messageToUserList) {

            sendSms(message, i)
            notifyProgressUpdate(messageToUserList.size, sendingMessageCount)
            Thread.sleep(1000*i.interval.toLong())

        }

        notifyStopMessaging()

        applicationContext.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_SENT_OK)
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_FAILURE_SEND)
                    SmsManager.RESULT_ERROR_NO_SERVICE -> updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_FAILURE_SEND)
                    SmsManager.RESULT_ERROR_NULL_PDU -> updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_FAILURE_SEND)
                    SmsManager.RESULT_ERROR_RADIO_OFF -> updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_FAILURE_SEND)
                }

                sentStatusMessageCount++

                if (sentStatusMessageCount >= messageToUserList.size) {
                    calculateMessageStatus()
                }
            }

        }, IntentFilter(Constants.SMS_SENT_INTENT))

        applicationContext.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_DELIVERED)
//                    Activity.RESULT_CANCELED ->
                }
            }
        }, IntentFilter(Constants.SMS_DELIVERED_INTENT))
    }

    private fun notifyStartMessaging() {

        notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                "Отправка SMS",
                NotificationManager.IMPORTANCE_LOW)

            notificationManager.createNotificationChannel(notificationChannel)
        }

        notification = NotificationCompat.Builder(applicationContext, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_message_black_18)
            .setLargeIcon(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.app_icon))
            .setSubText("subtext")
            .setContentTitle("Отправка SMS")
            .setProgress(0, 0, false)
            .setOngoing(true)

        notificationManager.notify(1, notification.build())

    }

    private fun notifyProgressUpdate(maxValue: Int, progress: Int) {
        notification.setProgress(maxValue, progress, false)

        notificationManager.notify(1, notification.build())
    }

    private fun notifyStopMessaging() {
        notification.setContentText("Отправка завершена")
            .setProgress(0,0,false)
            .setOngoing(false)

        notificationManager.notify(1, notification.build())
    }

    private fun sendSms(message: Message, messageToUser: MessageToUser) {
        sendingMessageCount++

        Log.d("UnicTag", "send $sendingMessageCount")
        val smsManager = SmsManager.getSmsManagerForSubscriptionId(messageToUser.subId)

        val requestCode: Int = messageToUser.id!!.toInt()

        val sentIntent = PendingIntent.getBroadcast(
            applicationContext,
            requestCode,
            Intent(Constants.SMS_SENT_INTENT).putExtra(Constants.KEY_MESSAGE_TO_USER_ID, messageToUser.id!!),
            0)
        val deliveredIntent = PendingIntent.getBroadcast(
            applicationContext,
            requestCode,
            Intent(Constants.SMS_DELIVERED_INTENT).putExtra(Constants.KEY_MESSAGE_TO_USER_ID, messageToUser.id!!),
            0)
//TODO:uncomment
//        smsManager.sendTextMessage(messageToUser.userPhoneNumber, null, message.messageText, sentIntent, deliveredIntent)
//        Toast.makeText(applicationContext, "send to ${messageToUser.userPhoneNumber}", Toast.LENGTH_SHORT).show()
    }

    private fun updateMessageToUser(id: Long, status: String) {
        var messageTuUser = messageToUserList.find { it.id == id }
        messageTuUser?.let { it ->
            it.status = status

            messageRepository.updateMessageToUser(it)
            this.messageToUserList.find {it1 -> it1.id == id }?.let { it2 -> it2.id = it.id }
        }
    }

    private fun calculateMessageStatus() {
        var status = Constants.STATUS_SENT_OK
        for (i in messageToUserList) {
            if (i.status == Constants.STATUS_FAILURE_SEND) {
                status = Constants.STATUS_FAILURE_SEND
            }
        }
        message.status = status
        messageRepository.updateMessage(message)
    }


}