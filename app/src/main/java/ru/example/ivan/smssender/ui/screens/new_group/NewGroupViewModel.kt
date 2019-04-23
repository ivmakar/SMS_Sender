package ru.example.ivan.smssender.ui.screens.new_group

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.ContactRepository
import ru.example.ivan.smssender.ui.uimodels.Contact
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import ru.example.ivan.smssender.utility.extensions.plusAssign
import java.text.FieldPosition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewGroupViewModel @Inject constructor(private var contactRepository: ContactRepository) : ViewModel() {

    private var _navigateComplete = SingleLiveEvent<Any>()
    val navigateComplete: LiveData<Any>
        get() = _navigateComplete

    private var _navigateAddContacts = SingleLiveEvent<Any>()
    val navigateAddContacts: LiveData<Any>
        get() = _navigateAddContacts

    val isLoading = ObservableBoolean()

    var contacts = MutableLiveData<ArrayList<Contact>>()

    private var compositeDisposable = CompositeDisposable()




    init{
        loadContacts()
    }

    private fun loadContacts(){
        isLoading.set(true)
        compositeDisposable += contactRepository
            .getContacts()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<ArrayList<Contact>>() {


                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: ArrayList<Contact>) {
                    contacts.value = t
                }

                override fun onComplete() {
                    isLoading.set(false)
                }

            })
    }


    fun newGroupFabCompleteOnClick() {
        _navigateComplete.call()
        //TODO: creating new group
    }


    fun newGroupAddContactsOnClick() {
        _navigateAddContacts.call()
    }

    fun selectItemByPosition(position: Int){
        contacts.value!![position].isSelected = !contacts.value?.get(position)?.isSelected!!
        contacts.value = contacts.value
    }

    fun deleteItemByPosition(position: Int){
        var pos = 0
        var i = 0
        while (pos != position && i < contacts.value!!.size){
            if (contacts.value!![i].isSelected)
                pos++
            i++
        }
        contacts.value!![i].isSelected = !contacts.value!![i].isSelected
        contacts.value = contacts.value
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }

    //TODO: get droupName.text
}