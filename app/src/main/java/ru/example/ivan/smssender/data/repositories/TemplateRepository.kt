package ru.example.ivan.smssender.data.repositories

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import ru.example.ivan.smssender.data.dbmodels.Template
import ru.example.ivan.smssender.utility.roomdb.DatabaseDao
import javax.inject.Inject

class TemplateRepository @Inject constructor(private val databaseDao: DatabaseDao) {

    private var templates = databaseDao.getTemplates()

    fun getTemplates() : LiveData<List<Template>> = templates

    fun saveNewTemplate(tmp: Template){
        databaseDao.insert(tmp)
    }

    fun updateTemplate(tmp: Template){
        databaseDao.update(tmp)
    }
}