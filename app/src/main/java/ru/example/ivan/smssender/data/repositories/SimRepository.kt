package ru.example.ivan.smssender.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager
import javax.inject.Inject

class SimRepository @Inject constructor(private val applicationContext: Context) {

    @SuppressLint("MissingPermission")
    fun getSubscriptionList(): MutableList<SubscriptionInfo>? {
        val subscriptionManager = applicationContext.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager

        val subscriptionList = subscriptionManager.activeSubscriptionInfoList

        return subscriptionList
    }
}