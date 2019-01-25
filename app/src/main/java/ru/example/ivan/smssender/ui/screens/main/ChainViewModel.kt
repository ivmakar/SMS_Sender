package ru.example.ivan.smssender.ui.screens.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import ru.example.ivan.smssender.data.ChainRepository
import ru.example.ivan.smssender.data.OnChainsReadyCallback
import ru.example.ivan.smssender.ui.uimodels.Chain

class ChainViewModel: ViewModel() {

    var chainRepository: ChainRepository = ChainRepository()

    val isLoading = ObservableBoolean()

    var chains = MutableLiveData<ArrayList<Chain>>()


    fun loadChains(){
        isLoading.set(true)
        chainRepository.getChains(object : OnChainsReadyCallback {
            override fun onDataReady(chainList: ArrayList<Chain>) {
                isLoading.set(false)
                chains.value = chainList
            }
        })
    }
}