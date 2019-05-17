package ru.example.ivan.smssender.ui.screens.template

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.TemplateRepository
import ru.example.ivan.smssender.ui.uimodels.Template
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class TemplateViewModel @Inject constructor(private var templateRepository: TemplateRepository): ViewModel() {


    val isLoading = ObservableBoolean()

    var templates = MutableLiveData<ArrayList<Template>>()

    private var compositeDisposable = CompositeDisposable()

    fun loadTemplates(){

        isLoading.set(true)
        compositeDisposable += templateRepository
            .getTemplates()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<ArrayList<Template>>() {


                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: ArrayList<Template>) {
                    templates.value = t
                }

                override fun onComplete() {
                    isLoading.set(false)
                }

            })
    }

    fun getTemplateTextByPosition(position: Int) = templates.value!![position].text

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}