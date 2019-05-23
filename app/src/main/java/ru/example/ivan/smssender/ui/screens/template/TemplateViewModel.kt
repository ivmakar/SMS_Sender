package ru.example.ivan.smssender.ui.screens.template

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.repositories.TemplateRepository
import ru.example.ivan.smssender.data.dbmodels.Template
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class TemplateViewModel @Inject constructor(private var templateRepository: TemplateRepository): ViewModel() {


    val isLoading = ObservableBoolean()

    var templates = templateRepository.getTemplates()

    fun getTemplateTextByPosition(position: Int) = templates.value!![position].templateText

}