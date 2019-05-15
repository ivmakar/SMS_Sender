package ru.example.ivan.smssender.ui.screens.new_message

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.Editable
import ru.example.ivan.smssender.ui.uimodels.Group
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewMessageViewModel @Inject constructor() : ViewModel() {

    private var _navigateComplete = SingleLiveEvent<Any>()
    val navigateComplete: LiveData<Any>
        get() = _navigateComplete

    var group = Group(1, "Братья", 30) //TODO:

    var isRandomInterval = ObservableBoolean(false)
    var isScheduleSending = ObservableBoolean(false)

    var intervalStart = ObservableInt(1)
    var intarvalEnd = ObservableInt(10)

    var scheduleDate: Calendar = Calendar.getInstance()
    var scheduleDateText = ObservableField<String>()

    var messageText = ObservableField<String>()

    var maxSimb = ObservableInt(160)
    var curSimb = ObservableInt(0)
    var curMessageCount = ObservableInt(1)

    fun onMessageTextChanged(s: Editable) {
        messageText.set(s.toString())
        if (messageText.get().isNullOrEmpty()){
            maxSimb.set(160)
            curSimb.set(0)
            curMessageCount.set(1)
            return
        }
        curSimb.set(messageText.get()!!.length)

        maxSimb.set(160)

        val str = messageText.get()!!
        for (i in str) {
            if (isRussianText(i)) {
                maxSimb.set(70)
                break
            }
        }

        if (curSimb.get() > maxSimb.get()) {
            if (maxSimb.get() == 160)
                maxSimb.set(153)
            else
                maxSimb.set(67)

        }

        curMessageCount.set(curSimb.get() / maxSimb.get() + 1)

    }

    private fun isRussianText (ch: Char): Boolean {
        if (ch in 'а'..'я' || ch in 'А'..'Я') {
            return true
        }
        return false
    }

    fun writeDate() {
        val sdf = SimpleDateFormat("EE, d MMMM, HH:mm", Locale.getDefault())
        scheduleDateText.set(sdf.format(Date(scheduleDate.timeInMillis)))
    }

    fun sendOnClick() {
        _navigateComplete.call()
    }


}