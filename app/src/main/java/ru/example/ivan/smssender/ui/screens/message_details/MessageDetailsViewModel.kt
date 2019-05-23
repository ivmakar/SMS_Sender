package ru.example.ivan.smssender.ui.screens.message_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.example.ivan.smssender.data.repositories.MessageDetailsRepository
import ru.example.ivan.smssender.ui.uimodels.MessageDetail
import javax.inject.Inject

class MessageDetailsViewModel @Inject constructor(private val messageDetailsRepository: MessageDetailsRepository) : ViewModel() {

    var messageDetailList = messageDetailsRepository.getMessageDetailListByMessageId(-1)

    fun loadData(messageId: Long): LiveData<ArrayList<MessageDetail>>? {
        messageDetailList = messageDetailsRepository.getMessageDetailListByMessageId(messageId)
        return messageDetailList
    }

    var isLoading = false

}