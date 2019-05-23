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

    private var sendingMessageCount = 0
    private var sentStatusMessageCount = 0
    private lateinit var message: Message

    private lateinit var notificationManager: NotificationManager
    private lateinit var notification: NotificationCompat.Builder


    lateinit var messageRepository: MessageRepository

    override fun doWork(): Result {
        val messageId = inputData.getLong(Constants.KEY_MESSAGE_ID, 0)
        val message = messageRepository.getMessageById(messageId)

        initSending()

        return Result.success()
    }

    fun initSending() {

//        applicationContext.registerReceiver(MessageStatusReceiver(), IntentFilter(Constants.SMS_SENT_INTENT).also { it.addAction(Constants.SMS_DELIVERED_INTENT) })

        applicationContext.startService(Intent(applicationContext, MessageStatusService::class.java))

        notifyStartMessaging()

        messageToUserList = messageRepository.getMessageToUserListByMessageId(message.id!!)

        for (i in messageToUserList) {

            sendSms(message, i)
            notifyProgressUpdate(messageToUserList.size, sendingMessageCount)
            Thread.sleep(1000*i.interval.toLong())

        }

        notifyStopMessaging()

        /*applicationContext.registerReceiver(object : BroadcastReceiver() {
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
                Log.d("smssenderWorker", "sentMessageCount = $sentStatusMessageCount")

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
        }, IntentFilter(Constants.SMS_DELIVERED_INTENT))*/
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
            .setContentTitle("Отправка SMS")
            .setProgress(0, 0, false)
            .setOngoing(true)

        notificationManager.notify(message.id!!.toInt(), notification.build())

    }

    private fun notifyProgressUpdate(maxValue: Int, progress: Int) {
        notification.setProgress(maxValue, progress, false)
            .setContentText("Отправка $progress/$maxValue")

        notificationManager.notify(message.id!!.toInt(), notification.build())
    }

    private fun notifyStopMessaging() {
        notification.setContentText("Отправка завершена")
            .setProgress(0,0,false)
            .setOngoing(false)

        notificationManager.notify(message.id!!.toInt(), notification.build())
    }

    private fun sendSms(message: Message, messageToUser: MessageToUser) {
        sendingMessageCount++

        Log.d("MyReceiver", "send $sendingMessageCount")
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

        Log.d("MyReceiver", "send Message. id = ${messageToUser.id}")

        smsManager.sendTextMessage(messageToUser.userPhoneNumber, null, message.messageText, sentIntent, deliveredIntent)

    }

    private fun updateMessageToUser(id: Long, status: String) {

        Log.d("MyReceiver", "update messageToUser. Id = $id")
        var messageToUser = messageRepository.getMessageToUserById(id)
        messageToUser?.let { it ->
            it.status = status

            messageRepository.updateMessageToUser(it)
        }
    }

    private fun calculateMessageStatus() {
        messageToUserList = messageRepository.getMessageToUserListByMessageId(message.id!!)
        var status = Constants.STATUS_SENT_OK
        for (i in messageToUserList) {
            if (i.status == Constants.STATUS_FAILURE_SEND) {
                status = Constants.STATUS_FAILURE_SEND
            }
        }

        Log.d("MyReceiver", "calculate message status = $status")
        message.status = status
        messageRepository.updateMessage(message)
    }

    override fun onStopped() {
        super.onStopped()
        Log.d("MyReceiver", "onStoppedWorker")
    }
}