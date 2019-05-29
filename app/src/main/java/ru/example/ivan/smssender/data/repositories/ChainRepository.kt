package ru.example.ivan.smssender.data.repositories

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import io.reactivex.Observable
import ru.example.ivan.smssender.data.dbmodels.Group
import ru.example.ivan.smssender.ui.uimodels.Chain
import ru.example.ivan.smssender.utility.roomdb.DatabaseDao
import javax.inject.Inject

class ChainRepository @Inject constructor(private val databaseDao: DatabaseDao) {

    val groupsList = databaseDao.getGroups()

    fun getChains() = Transformations.map(groupsList) {
        val chains = ArrayList<Chain>()
        for (i in it) {
            val countMessages = databaseDao.getMessageCountByGroupId(i.id!!)
            if (!(countMessages.isEmpty() || countMessages.first() == 0)) {
                val lastMessage = databaseDao.getLastMessagesByGroupId(i.id)
                chains.add(Chain(i.id, i.groupName, lastMessage.last().messageText, countMessages.first(), lastMessage.last().getDateFormated())) //TODO:LastDate
            }
        }
        chains
    }
}