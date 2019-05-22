package ru.example.ivan.smssender.ui.screens.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
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

    var messages = MutableLiveData<ArrayList<Message>>()

    var curGroup = MutableLiveData<Group>()

    private var compositeDisposable = CompositeDisposable()

    fun loadGroup(groupId: Long){
        compositeDisposable += groupRepository
            .getGroupById(groupId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<Group>() {


                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: Group) {
                    curGroup.value = t
                }

                override fun onComplete() {
                }

            })
    }

    fun loadMessages(groupId: Long){

        isLoading.set(true)
        compositeDisposable += messageRepository
            .getMessagesByGroupId(groupId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<ArrayList<Message>>() {


                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: ArrayList<Message>) {
                    messages.value = t
                }

                override fun onComplete() {
                    isLoading.set(false)
                }

            })
    }

    fun getMessageIdByPosition(position: Int): Long {
        return messages.value?.get(position)?.id!!
    }

    fun messageOnClick() {
        _navigateToNewMessage.call()
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}