package ru.example.ivan.smssender.ui.screens.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.repositories.GroupRepository
import ru.example.ivan.smssender.data.dbmodels.Group
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class GroupViewModel @Inject constructor(private var groupRepository: GroupRepository) : ViewModel() {

    private var _navigateToNewGroup = SingleLiveEvent<Any>()
    val navigateToNewGroup: LiveData<Any>
        get() = _navigateToNewGroup

    val isLoading = ObservableBoolean()

    var groups = MutableLiveData<ArrayList<Group>>()

    private var compositeDisposable = CompositeDisposable()

    init{
        loadGroups()
    }

    private fun loadGroups(){
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

    fun groupOnClick() {
        _navigateToNewGroup.call()
    }

    fun getGroupByPosition (position: Int) = groups.value?.get(position)

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}