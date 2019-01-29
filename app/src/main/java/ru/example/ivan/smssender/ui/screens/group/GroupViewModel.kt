package ru.example.ivan.smssender.ui.screens.group

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.GroupRepository
import ru.example.ivan.smssender.ui.uimodels.Group
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class GroupViewModel @Inject constructor(var groupRepository: GroupRepository) : ViewModel() {

    val isLoading = ObservableBoolean()

    var groups = MutableLiveData<ArrayList<Group>>()

    private var compositeDisposable = CompositeDisposable()

    fun loadGroups(){
        isLoading.set(true)
        compositeDisposable += groupRepository
            .getGroups()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<ArrayList<Group>>() {

                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: ArrayList<Group>) {
                    groups.value = t
                }

                override fun onComplete() {
                    isLoading.set(false)
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}