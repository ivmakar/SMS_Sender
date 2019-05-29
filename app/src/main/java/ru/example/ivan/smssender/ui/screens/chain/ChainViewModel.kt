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

    var chains = chainRepository.getChains()

    fun loadChains(): LiveData<ArrayList<Chain>>? {
        chains = chainRepository.getChains()
        return chains
    }

    fun chainOnClick() {
        _navigateToGroups.call()
    }

    fun getChainByPosition(position: Int) : Chain {
        return chains.value?.get(position)!!
    }
}