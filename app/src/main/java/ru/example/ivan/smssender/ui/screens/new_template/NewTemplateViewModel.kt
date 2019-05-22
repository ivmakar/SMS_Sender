package ru.example.ivan.smssender.ui.screens.new_template


import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.repositories.TemplateRepository
import ru.example.ivan.smssender.data.dbmodels.Template
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject

class NewTemplateViewModel @Inject constructor(private var templateRepository: TemplateRepository): ViewModel() {


    var templateName = ObservableField<String>()
    var templateText = ObservableField<String>()
    var errMessage = String()
    var templates = ArrayList<Template>()

    var maxSimb = ObservableInt(160)
    var curSimb = ObservableInt(0)
    var curMessageCount = ObservableInt(1)

    private var compositeDisposable = CompositeDisposable()

    init {
        loadTemplates()
    }

    private fun loadTemplates(){

        compositeDisposable += templateRepository
            .getTemplates()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<ArrayList<Template>>() {


                override fun onError(e: Throwable) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: ArrayList<Template>) {
                    templates = t
                }

                override fun onComplete() {
                }

            })
    }

    fun isCorrectData(): Boolean {
        if (templateName.get().isNullOrEmpty() || templateText.get().isNullOrEmpty()) {
            errMessage = "Заполните все поля"
            return false
        }

        for (i in templates) {
            if (i.name.equals(templateName.get())) {
                errMessage = "Шаблон с таким названием уже существует"
                return false
            }
        }

        return true
    }

    fun onMessageTextChanged(s: Editable) {
        templateText.set(s.toString())
        if (templateText.get().isNullOrEmpty()){
            maxSimb.set(160)
            curSimb.set(0)
            curMessageCount.set(1)
            return
        }
        curSimb.set(templateText.get()!!.length)

        maxSimb.set(160)

        val str = templateText.get()!!
        for (i in str) {
            if (isRussianText(i)) {
                maxSimb.set(70)
                break
            }
        }

        if (curSimb.get() > maxSimb.get()) {
            if (maxSimb.get() == 160)
                maxSimb.set(153)
            else
                maxSimb.set(67)

        }

        if (curMessageCount.get() > 3) {
            templateText.set(templateText.get()!!.substring(0, maxSimb.get() * 3))
        }

        curMessageCount.set(curSimb.get() / maxSimb.get() + 1)

    }

    private fun isRussianText (ch: Char): Boolean {
        if (ch in 'а'..'я' || ch in 'А'..'Я') {
            return true
        }
        return false
    }

    fun saveTemplate(){
        templateRepository.saveNewTemplate(
            Template(
                null,
                templateName.get()!!,
                templateText.get()!!
            )
        )
    }


    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}