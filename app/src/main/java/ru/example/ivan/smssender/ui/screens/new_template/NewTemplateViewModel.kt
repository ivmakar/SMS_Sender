package ru.example.ivan.smssender.ui.screens.new_template

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.TemplateRepository
import ru.example.ivan.smssender.ui.uimodels.Template
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class NewTemplateViewModel @Inject constructor(private var templateRepository: TemplateRepository): ViewModel() {


    var templateName = ObservableField<String>()
    var templateText = ObservableField<String>()
    var errMessage = String()

    fun isCorrectData(): Boolean {
        if (templateName.get().isNullOrEmpty() || templateText.get().isNullOrEmpty()) {
            errMessage = "Заполните все поля"
            return false
        }
        //TODO: if templates contains this template name then return false
        return true
    }

    fun saveTemplate(){
        templateRepository.saveNewTemplate(Template(null, templateName.get()!!, templateText.get()!!))
    }

}