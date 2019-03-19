package ru.example.ivan.smssender.ui.screens.new_message

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.example.ivan.smssender.ui.uimodels.Group
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import java.util.*
import javax.inject.Inject

class NewMessageViewModel @Inject constructor() : ViewModel() {

    private var _navigateComplete = SingleLiveEvent<Any>()
    val navigateComplete: LiveData<Any>
        get() = _navigateComplete

    var group = Group(1, "Братья", 30)

    var isRandomInterval = false
    var isScheduleSending = false

    var intervalStart = 1
    var intarvalEnd = 10

    var date = Date()
    var time = date.time

    var messageText = MutableLiveData<String>()

    var maxSimb = 160
    var curSimb = 0
    var curMessageCount = 1


    fun onCheckedChangedRandomInterval() {

    }

    fun onCheckedChangedScheduleSending() {

    }


    fun sendOnClick() {
        _navigateComplete.call()
    }


}