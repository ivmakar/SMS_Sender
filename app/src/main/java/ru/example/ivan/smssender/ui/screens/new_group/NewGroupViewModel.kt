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

class NewGroupViewModel @Inject constructor(contactRepository: ContactRepository) : ViewModel() {

    private var _navigateComplete = SingleLiveEvent<Any>()
    val navigateComplete: LiveData<Any>
        get() = _navigateComplete

    private var _navigateAddContacts = SingleLiveEvent<Any>()
    val navigateAddContacts: LiveData<Any>
        get() = _navigateAddContacts
/*
    private var _notifyAddContact = SingleLiveEvent<Any>()
    val notifyAddContact: LiveData<Any>
        get() = _notifyAddContact

    private var _notifyRemoveContact = SingleLiveEvent<Any>()
    val notifyRemoveContact: LiveData<Any>
        get() = _notifyRemoveContact

    var notifyPositionStart: Int = 0
    var notifyPositionEnd: Int = 0
    var multipleChanging = false*/

    val isLoading = ObservableBoolean()

//    private var groupName = String()

    var contacts = MutableLiveData<ArrayList<Contact>>()
//    var contacts = ArrayList<Contact>()

    private var compositeDisposable = CompositeDisposable()

/*    init{
        contacts.value = ArrayList()
    }*/



    fun newGroupCompleteOnClick() {
        _navigateComplete.call()
        //TODO: creating new group
    }

    fun newGroupAddContactsOnClick() {
        _navigateAddContacts.call()
    }

/*    fun updateGroupNameField(newGroupName: String){
        groupName = newGroupName
    }*/

/*    fun addContacts(selectedContacts: ArrayList<Contact>) {

        for (i in selectedContacts) {
            i.isSelected = false
            if (!isArrayContainElem(contacts.value!!, i))
                contacts.value?.add(i)
        }
        contacts.value = contacts.value
    }*/

    fun addContacts(selectedContacts: ArrayList<Contact>) {
/*        var addContacts = ArrayList<Contact>()
        for (i in selectedContacts) {
            i.isSelected = false
            if (!isArrayContainElem(contacts, i))
                addContacts.add(i)
        }

        when {
            addContacts.isEmpty() -> return
            addContacts.size == 1 -> multipleChanging = false
            addContacts.size > 1 -> {
                multipleChanging = true
                notifyPositionEnd = contacts.size + addContacts.size - 1
            }
        }
        contacts.addAll(addContacts)
        notifyPositionStart = contacts.size
        _notifyAddContact.call()*/

        this.contacts.value = selectedContacts

    }

    private fun isArrayContainElem(array: ArrayList<Contact>, contact: Contact) : Boolean {
        var result = false
        for (i in array) {
            if (i.id == contact.id)
                result = true
        }
        return result
    }

/*    fun deleteItemByPosition(position: Int){
        contacts.value?.removeAt(position)
        contacts.value = contacts.value
    }*/

    fun deleteItemByPosition(position: Int){
        contacts.value?.removeAt(position)
//        notifyPositionStart = position
//        _notifyRemoveContact.call()
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }

    //TODO: get droupName.text
}