package ru.example.ivan.smssender.data.repositories

import io.reactivex.Observable
import ru.example.ivan.smssender.data.dbmodels.Template
import ru.example.ivan.smssender.utility.roomdb.DatabaseDao
import javax.inject.Inject

class TemplateRepository @Inject constructor(private val databaseDao: DatabaseDao) {

    fun getTemplates(): Observable<ArrayList<Template>> {

        val templates = databaseDao.getTemplates() as ArrayList<Template>

        return Observable.just(templates)
    }

    fun saveNewTemplate(tmp: Template){
        databaseDao.insert(tmp)
    }

    fun updateTemplate(tmp: Template){
        databaseDao.update(tmp)
    }
}