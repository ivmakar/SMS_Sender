package ru.example.ivan.smssender.ui.screens.contacts

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
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

    private var _showToast = SingleLiveEvent<Any>()
    val showToast: LiveData<Any>
        get() = _showToast

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
                }

            })
    }

    fun contactOnClick() {
        for (i in contacts.value!!) {
            if (i.isSelected)
                selectedContacts.add(i)
        }
        if (selectedContacts.isEmpty()) {
            _showToast.call()
            return
        }
        _navigateComplete.call()
    }

    fun selectItemByPosition(position: Int) {

        contacts.value?.get(position)?.isSelected = !contacts.value?.get(position)?.isSelected!!
        contacts.value = contacts.value

    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}