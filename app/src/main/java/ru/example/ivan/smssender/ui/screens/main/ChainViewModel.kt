package ru.example.ivan.smssender.ui.screens.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.ChainRepository
import ru.example.ivan.smssender.ui.uimodels.Chain
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class ChainViewModel @Inject constructor(var chainRepository: ChainRepository): ViewModel() {



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

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}