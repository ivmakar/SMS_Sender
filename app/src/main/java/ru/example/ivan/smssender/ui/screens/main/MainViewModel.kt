package ru.example.ivan.smssender.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.example.ivan.smssender.data.ChainRepository
import ru.example.ivan.smssender.ui.uimodels.Chain
import ru.example.ivan.smssender.ui.uimodels.Contact
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {



}