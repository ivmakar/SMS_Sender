package ru.example.ivan.smssender.data.repositories

import io.reactivex.Observable
import ru.example.ivan.smssender.data.dbmodels.Message
import ru.example.ivan.smssender.data.dbmodels.MessageToUser
import ru.example.ivan.smssender.utility.roomdb.DatabaseDao
import javax.inject.Inject

class MessageRepository @Inject constructor(private val databaseDao: DatabaseDao) {

    fun getMessagesByGroupId(groupId: Long): Observable<ArrayList<Message>> {
        var messagesList = ArrayList<Message>()

        messagesList = databaseDao.getMessagesByGroupId(groupId) as ArrayList<Message>

        return Observable.just(messagesList)
    }

    fun saveMessage(message: Message, messageToUserList: ArrayList<MessageToUser>) {
        val messageId = databaseDao.insert(message)
        for (i in messageToUserList) {
            i.messageId = messageId
            databaseDao.insert(i)
        }
    }

    fun updateMessageToUser(messaageToUser: MessageToUser) {
        databaseDao.update(messaageToUser)
    }

    fun deleteMessage(message: Message) {
        val messageToUserList
                = databaseDao.getMessageToUserByMessageId(message.id!!) as ArrayList<MessageToUser>

        for (i in messageToUserList) {
            databaseDao.delete(i)
        }

        databaseDao.delete(message)
    }
}