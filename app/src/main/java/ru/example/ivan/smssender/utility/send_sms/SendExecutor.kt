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
import android.content.Intent
import android.content.BroadcastReceiver
import android.app.PendingIntent
import ru.example.ivan.smssender.data.dbmodels.MessageToUser


class SendExecutor @Inject constructor(
    private val messageRepository: MessageRepository,
    private val applicationContext: Context) {

    private var messageToUserList = ArrayList<MessageToUser>()
    private val subscriptionManager
            = applicationContext.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

    private var sendingMessageCount = 0
    private lateinit var message: Message


    fun initSending(msg: Message) {
        message = msg
        messageToUserList = messageRepository.getMessageToUserListByMessageId(message.id!!)

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

                if (sendingMessageCount < messageToUserList.size) {
                    sendSms(message, messageToUserList[sendingMessageCount])
                } else {
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

        sendSms(message, messageToUserList[sendingMessageCount])
    }

    private fun sendSms(message: Message, messageToUser: MessageToUser) {
        sendingMessageCount++
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

        smsManager.sendTextMessage(messageToUser.userPhoneNumber, null, message.messageText, sentIntent, deliveredIntent)
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