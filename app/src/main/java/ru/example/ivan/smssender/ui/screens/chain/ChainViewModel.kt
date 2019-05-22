package ru.example.ivan.smssender.ui.screens.chain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.repositories.ChainRepository
import ru.example.ivan.smssender.ui.uimodels.Chain
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class ChainViewModel @Inject constructor(private var chainRepository: ChainRepository): ViewModel() {

    private var _navigateToGroups = SingleLiveEvent<Any>()
    val navigateToNewMessage: LiveData<Any>
        get() = _navigateToGroups

    val isLoading = ObservableBoolean()

    var chains = MutableLiveData<ArrayList<Chain>>()

    private var compositeDisposable = CompositeDisposable()

    fun loadChains(){
        isLoading.set(true)
        compositeDisposable += chainRepository
            .getChains()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<ArrayList<Chain>>() {


                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: ArrayList<Chain>) {
                    chains.value = t
                }

                override fun onComplete() {
                    isLoading.set(false)
                }

            })
    }

    fun chainOnClick() {
        _navigateToGroups.call()
    }

    fun getChainByPosition(position: Int) : Chain {
        return chains.value?.get(position)!!
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}