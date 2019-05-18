package ru.example.ivan.smssender.data.repositories

import io.reactivex.Observable
import ru.example.ivan.smssender.data.dbmodels.Template
import ru.example.ivan.smssender.utility.roomdb.DatabaseDao
import javax.inject.Inject

class TemplateRepository @Inject constructor(private val databaseDao: DatabaseDao) {

    fun getTemplates(): Observable<ArrayList<Template>> {

        val templates = databaseDao.getTemplates() as ArrayList<Template>

        return Observable.just(templates)
/*        var arrayList = ArrayList<Template>()
        arrayList.add(Template(1, "Шаблон1", "Текст шаблона 1. Текст шаблона 1. Текст шаблона 1. Текст шаблона 1. Текст шаблона 1. Текст шаблона 1. "))
        arrayList.add(Template(2, "Шаблон2", "Текст шаблона 2. Текст шаблона 2. Текст шаблона 2. Текст шаблона 2."))
        arrayList.add(Template(3, "Template3", "Template3 messageText. Template3 messageText. Template3 messageText. Template3 messageText. Template3 messageText. Template3 messageText. "))

        return Observable.just(arrayList).delay(1, TimeUnit.SECONDS)*/
    }

    fun saveNewTemplate(tmp: Template){
        databaseDao.insert(tmp)
    }
}