package ru.example.ivan.smssender.utility.send_sms

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import ru.example.ivan.smssender.R
import ru.example.ivan.smssender.data.dbmodels.Message
import ru.example.ivan.smssender.data.dbmodels.MessageToUser
import ru.example.ivan.smssender.data.repositories.MessageRepository
import ru.example.ivan.smssender.utility.Constants
import javax.inject.Inject

class MessageStatusReceiver @Inject constructor(private val messageRepository: MessageRepository) : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            Log.d("MyReceiver", "messageRepository is available")
        } catch (e: UninitializedPropertyAccessException) {
            Log.d("MyReceiver", "messageRepository is not available")
        }
        if (intent != null) {
            Toast.makeText(context, intent.action, Toast.LENGTH_LONG).show()
            when (intent.action) {
                Constants.SMS_SENT_INTENT -> when (resultCode) {
                    Activity.RESULT_OK -> Log.d("MyReceiver", "receive sent. Status = $resultCode. id = " + intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0))/*updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_SENT_OK)*/
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> Log.d("MyReceiver", "receive sent. Status = $resultCode. id = " + intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0)) /*updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_FAILURE_SEND)*/
                    SmsManager.RESULT_ERROR_NO_SERVICE -> Log.d("MyReceiver", "receive sent. Status = $resultCode. id = " + intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0)) /*updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_FAILURE_SEND)*/
                    SmsManager.RESULT_ERROR_NULL_PDU -> Log.d("MyReceiver", "receive sent. Status = $resultCode. id = " + intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0)) /*updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_FAILURE_SEND)*/
                    SmsManager.RESULT_ERROR_RADIO_OFF -> Log.d("MyReceiver", "receive sent. Status = $resultCode. id = " + intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0)) /*updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_FAILURE_SEND)*/
                }
                Constants.SMS_DELIVERED_INTENT -> when (resultCode) {
                    Activity.RESULT_OK -> Log.d("MyReceiver", "receive delivered. Status = $resultCode. id = " + intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0)) /*updateMessageToUser(
                        intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0),
                        Constants.STATUS_DELIVERED)*/
//                    Activity.RESULT_CANCELED ->
                }
            }
        }
    }

/*    private var messageToUserList = ArrayList<MessageToUser>()

    private var sendingMessageCount = 0
    private var sentStatusMessageCount = 0

    private lateinit var notificationManager: NotificationManager
    private lateinit var notification: NotificationCompat.Builder

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

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
                Log.d("smssenderWorker", "sentMessageCount = $sentStatusMessageCount")

                if (sentStatusMessageCount >= messageToUserList.size) {
                    calculateMessageStatus(intent.getLongExtra(Constants.KEY_MESSAGE_TO_USER_ID, 0))
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

    private fun updateMessageToUser(id: Long, status: String) {

        Log.d("smssenderWorker", "update messageToUser. Id = $id")
        var messageToUser = messageRepository.getMessageToUserById(id)
        messageToUser?.let { it ->
            it.status = status

            messageRepository.updateMessageToUser(it)
        }
    }

    private fun calculateMessageStatus(messageToUserId: Long) {
        val message = messageRepository.getMessageById(messageRepository.getMessageToUserById(messageToUserId).messageId)
        messageToUserList = messageRepository.getMessageToUserListByMessageId(message.id!!)
        var status = Constants.STATUS_SENT_OK
        for (i in messageToUserList) {
            if (i.status == Constants.STATUS_FAILURE_SEND) {
                status = Constants.STATUS_FAILURE_SEND
            }
        }

        Log.d("smssenderWorker", "calculate message status = $status")
        message.status = status
        messageRepository.updateMessage(message)
    }*/
}
