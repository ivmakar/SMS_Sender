package ru.example.ivan.smssender.ui.screens.messages

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
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

    private var _navigateToGroups = SingleLiveEvent<Any>()
    val navigateToGroups: LiveData<Any>
        get() = _navigateToGroups

    val isLoading = ObservableBoolean()

    var messages = MutableLiveData<ArrayList<Message>>()

    private var compositeDisposable = CompositeDisposable()

    init{
        loadChains()
    }

    private fun loadChains(){
        isLoading.set(true)
        compositeDisposable += messageRepository
            .getMessages()
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
        _navigateToGroups.call()
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}