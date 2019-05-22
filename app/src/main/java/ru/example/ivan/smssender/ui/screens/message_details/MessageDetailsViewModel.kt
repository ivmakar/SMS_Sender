package ru.example.ivan.smssender.ui.screens.message_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.repositories.MessageDetailsRepository
import ru.example.ivan.smssender.ui.uimodels.MessageDetail
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class MessageDetailsViewModel @Inject constructor(private val messageDetailsRepository: MessageDetailsRepository) : ViewModel() {

    var messageDetailList = MutableLiveData<ArrayList<MessageDetail>>()

    var isLoading = false

    private var compositeDisposable = CompositeDisposable()

    fun loadMessageDetailList(messageId: Long){
        isLoading = true
        compositeDisposable += messageDetailsRepository
            .getMessageDetailListByMessageId(messageId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<ArrayList<MessageDetail>>() {


                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: ArrayList<MessageDetail>) {
                    messageDetailList.value = t
                }

                override fun onComplete() {
                    isLoading = false
                }

            })
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }

}