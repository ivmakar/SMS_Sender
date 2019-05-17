package ru.example.ivan.smssender.ui.screens.new_template


import androidx.lifecycle.ViewModel
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
    var templates = ArrayList<Template>()

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

    fun saveTemplate(){
        templateRepository.saveNewTemplate(Template(null, templateName.get()!!, templateText.get()!!))
    }


    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
}