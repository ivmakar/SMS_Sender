package ru.example.ivan.smssender.ui.screens.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Transformations
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.dbmodels.Group
import ru.example.ivan.smssender.data.repositories.MessageRepository
import ru.example.ivan.smssender.data.dbmodels.Message
import ru.example.ivan.smssender.data.repositories.GroupRepository
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class MessagesViewModel @Inject constructor(private var messageRepository: MessageRepository, private val groupRepository: GroupRepository): ViewModel() {

    private var _navigateToNewMessage = SingleLiveEvent<Any>()
    val navigateToNewMessage: LiveData<Any>
        get() = _navigateToNewMessage

    val isLoading = ObservableBoolean()

    var group = MutableLiveData<Group>()

    var messages = messageRepository.getMessagesByGroupId(-1)

    fun loadGroup(groupId: Long) {
        group.value = groupRepository.getGroupById(groupId)
    }

    fun loadMessages(groupId: Long): LiveData<List<Message>> {
        messages = messageRepository.getMessagesByGroupId(groupId)
        return messages
    }

    fun getMessageIdByPosition(position: Int): Long {
        return messages.value?.get(position)?.id!!
    }

    fun messageOnClick() {
        _navigateToNewMessage.call()
    }
}