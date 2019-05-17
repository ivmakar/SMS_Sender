package ru.example.ivan.smssender.ui.screens.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.MessageRepository
import ru.example.ivan.smssender.ui.uimodels.Message
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class MessagesViewModel @Inject constructor(private var messageRepository: MessageRepository): ViewModel() {

    private var _navigateToNewMessage = SingleLiveEvent<Any>()
    val navigateToNewMessage: LiveData<Any>
        get() = _navigateToNewMessage

    val isLoading = ObservableBoolean()

    var messages = MutableLiveData<ArrayList<Message>>()

    private var compositeDisposable = CompositeDisposable()

/*    init{
        loadChains()
    }*/

    public fun loadChains(groupId: Int){

        isLoading.set(true)
        compositeDisposable += messageRepository
            .getMessages(groupId)
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