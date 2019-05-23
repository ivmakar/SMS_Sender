package ru.example.ivan.smssender.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import ru.example.ivan.smssender.data.dbmodels.Group
import ru.example.ivan.smssender.data.dbmodels.UserToGroup
import ru.example.ivan.smssender.ui.uimodels.Contact
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions
import ru.example.ivan.smssender.utility.roomdb.DatabaseDao
import javax.inject.Inject
import kotlin.collections.ArrayList

class GroupRepository @Inject constructor(private val databaseDao: DatabaseDao) {

    private var groups = databaseDao.getGroups()

    fun getAllGroups() : LiveData<List<Group>> = groups


    fun getGroupById(groupId: Long) = databaseDao.getGroupById(groupId)

    fun saveGroup(group: Group, contactList: ArrayList<Contact>) {
        val groupId = databaseDao.insert(group)

        for (i in contactList) {
            databaseDao.insert(UserToGroup(null, groupId, AppFunctions.standartizePhoneNumber(i.phoneNumber)))
        }
    }

    fun updateGroup(group: Group) {
        databaseDao.update(group)
    }

    fun deleteGroup(group: Group) {
        databaseDao.delete(group)
    }
}