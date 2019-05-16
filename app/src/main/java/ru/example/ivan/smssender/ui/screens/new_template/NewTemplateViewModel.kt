package ru.example.ivan.smssender.ui.screens.new_template

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
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
        templateRepository.saveNewTemplate(Template(0, templateName.get()!!, templateText.get()!!))
    }

}