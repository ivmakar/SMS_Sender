package ru.example.ivan.smssender.utility.send_sms

import android.content.Context
import android.os.AsyncTask
import android.telephony.SmsManager
import android.telephony.SubscriptionManager
import ru.example.ivan.smssender.data.dbmodels.Message
import ru.example.ivan.smssender.data.repositories.MessageRepository
import ru.example.ivan.smssender.utility.Constants
import javax.inject.Inject
import android.content.IntentFilter
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import android.content.BroadcastReceiver
import android.app.PendingIntent



class SendExecutor @Inject constructor(
    private val messageRepository: MessageRepository,
    private val applicationContext: Context) : AsyncTask<Message, Int, Int>() {

    override fun onProgressUpdate(vararg values: Int?) {

    }

    override fun onPostExecute(result: Int?) {

    }

    override fun onPreExecute() {

    }

    override fun doInBackground(vararg params: Message?): Int {
        if (params.first() == null){
            return Constants.STATUS_ERROR_NO_PARAMETERS
        }

        val list = messageRepository.getMessageToUserListByMessageId(params.first()!!.id!!)

        val subscriptionManager = applicationContext.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

        for ((count, i) in list.withIndex()) {
            val smsManager = SmsManager.getSmsManagerForSubscriptionId(i.subId)

            val SENT = "SMS_SENT"
            val DELIVERED = "SMS_DELIVERED"

            val sentIntent = PendingIntent.getBroadcast(applicationContext, 0, Intent(SENT), 0)
            val deliveredIntent = PendingIntent.getBroadcast(applicationContext, 0, Intent(DELIVERED), 0)

            applicationContext.registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    when (resultCode) {
                        Activity.RESULT_OK -> Toast.makeText(applicationContext, "SMS sent", Toast.LENGTH_SHORT).show()
                        SmsManager.RESULT_ERROR_GENERIC_FAILURE -> Toast.makeText(
                            applicationContext,
                            "Generic failure",
                            Toast.LENGTH_SHORT
                        ).show()
                        SmsManager.RESULT_ERROR_NO_SERVICE -> Toast.makeText(
                            applicationContext,
                            "No service",
                            Toast.LENGTH_SHORT
                        ).show()
                        SmsManager.RESULT_ERROR_NULL_PDU -> Toast.makeText(
                            applicationContext,
                            "Null PDU",
                            Toast.LENGTH_SHORT
                        ).show()
                        SmsManager.RESULT_ERROR_RADIO_OFF -> Toast.makeText(
                            applicationContext,
                            "Radio off",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }, IntentFilter(SENT))

            applicationContext.registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(arg0: Context, arg1: Intent) {
                    when (resultCode) {
                        Activity.RESULT_OK -> Toast.makeText(
                            applicationContext,
                            "SMS delivered",
                            Toast.LENGTH_SHORT
                        ).show()
                        Activity.RESULT_CANCELED -> Toast.makeText(
                            applicationContext,
                            "SMS not delivered",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }, IntentFilter(DELIVERED))

            smsManager.sendTextMessage(i.userPhoneNumber, null, params.first()!!.messageText, sentIntent, deliveredIntent)
        }

        return 0
    }


}