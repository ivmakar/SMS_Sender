package ru.example.ivan.smssender.ui.screens.contacts

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.AppModule
import ru.example.ivan.smssender.data.ContactRepository
import ru.example.ivan.smssender.ui.uimodels.Contact
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class ContactViewModel @Inject constructor(private var contactRepository: ContactRepository): ViewModel() {

    private var _navigateComplete = SingleLiveEvent<Any>()
    val navigateComplete: LiveData<Any>
        get() = _navigateComplete


    val isLoading = ObservableBoolean()

    var contacts = MutableLiveData<ArrayList<Contact>>()
    var selectedContacts = ArrayList<Contact>()

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
                    for (a in selectedContacts) {
                        contacts.value!!.find { it.id == a.id }!!.isSelected = true
                    }
                    contacts.value = contacts.value
                }

            })
    }

    fun contactOnClick() {
/*        for (i in contacts.value!!) {
            if (i.isSelected)
                selectedContacts.add(i)
        }*/
        _navigateComplete.call()
    }

    fun selectItemByPosition(position: Int) {

        contacts.value?.get(position)?.isSelected = !contacts.value?.get(position)?.isSelected!!
        contacts.value = contacts.value
        if (contacts.value?.get(position)?.isSelected!!){
            selectedContacts.add(contacts.value?.get(position)!!)
        } else {
            selectedContacts.remove(selectedContacts.find { val b = contacts.value!![position]
                it.id == b.id
            })
        }

    }

    fun initSelectedContacts(selectedContacts: ArrayList<Contact>?){
        if (selectedContacts != null) {
 /*           while (isLoading.get()) {}
            for (a in selectedContacts) {
                contacts.value!!.find { it.id == a.id }!!.isSelected = true
            }

            contacts.value = contacts.value*/
            this.selectedContacts = selectedContacts
        }
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}