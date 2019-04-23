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
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

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
    var selectedContacts = MutableLiveData<Set<Int>>()

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
        contacts.value!![position].isSelected = !contacts.value!![position].isSelected
        contacts.postValue(contacts.value)
        if (contacts.value!![position].isSelected){
            if (selectedContacts.value == null)
                selectedContacts.value = sortedSetOf()

            selectedContacts.value = selectedContacts.value!!.plus(position)
        } else {

            selectedContacts.value = selectedContacts.value!!.minus(position)
        }

    }

    fun deleteItemByPosition(position: Int){
        contacts.value!![selectedContacts.value!!.elementAt(position)].isSelected = !contacts.value!![selectedContacts.value!!.elementAt(position)].isSelected
        contacts.postValue(contacts.value)
        if (contacts.value!![selectedContacts.value!!.elementAt(position)].isSelected){
            if (selectedContacts.value == null)
                selectedContacts.value = sortedSetOf()

            selectedContacts.value = selectedContacts.value!!.plus(selectedContacts.value!!.elementAt(position))
        } else {

            selectedContacts.value = selectedContacts.value!!.minus(selectedContacts.value!!.elementAt(position))
        }

    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }

    //TODO: get droupName.text
}