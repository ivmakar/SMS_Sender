package ru.example.ivan.smssender.data.repositories

import io.reactivex.Observable
import ru.example.ivan.smssender.data.dbmodels.Group
import ru.example.ivan.smssender.ui.uimodels.Chain
import ru.example.ivan.smssender.utility.roomdb.DatabaseDao
import javax.inject.Inject

class ChainRepository @Inject constructor(private val databaseDao: DatabaseDao) {

    fun getChains() : Observable<ArrayList<Chain>> {
        var chainsList = ArrayList<Chain>()

        val groupsList = databaseDao.getGroups() as ArrayList<Group>

        for (i in groupsList) {
            val countMessages = databaseDao.getMessageCountByGroupId(i.id!!)
            if (!(countMessages.isEmpty() || countMessages.first() == 0)) {
                val lastMessage = databaseDao.getLastMessagesByGroupId(i.id)
                chainsList.add(Chain(i.id, i.groupName, lastMessage.last().messageText, countMessages.first(), "")) //TODO:LastDate
            }
        }

        return Observable.just(chainsList)
    }
}