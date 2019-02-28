package ru.example.ivan.smssender.ui.screens.new_message

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import ru.example.ivan.smssender.ui.uimodels.Group
import ru.example.ivan.smssender.ui.uimodels.Message
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import java.util.*
import javax.inject.Inject

class NewMessageViewModel : ViewModel() {

    private var _navigateToGroups = SingleLiveEvent<Any>()
    val navigateToGroups: LiveData<Any>
        get() = _navigateToGroups

    lateinit var group: Group

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


    fun messageOnClick() {
        _navigateToGroups.call()
    }


}