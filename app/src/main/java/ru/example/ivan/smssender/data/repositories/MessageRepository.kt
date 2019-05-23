package ru.example.ivan.smssender.data.repositories

import ru.example.ivan.smssender.data.dbmodels.Message
import ru.example.ivan.smssender.data.dbmodels.MessageToUser
import ru.example.ivan.smssender.utility.roomdb.DatabaseDao
import javax.inject.Inject

class MessageRepository @Inject constructor(private val databaseDao: DatabaseDao) {

    fun getMessagesByGroupId(groupId: Long) = databaseDao.getMessagesByGroupId(groupId)

    fun saveMessage(message: Message, messageToUserList: ArrayList<MessageToUser>): Long {
        val messageId = databaseDao.insert(message)
        for (i in messageToUserList) {
            i.messageId = messageId
            databaseDao.insert(i)
        }
        return messageId
    }

    fun getMessageToUserListLiveByMessageId(messageId: Long) = databaseDao.getMessageToUserLiveByMessageId(messageId)

    fun getMessageToUserListByMessageId(messageId: Long) = databaseDao.getMessageToUserByMessageId(messageId)

    fun updateMessageToUser(messaageToUser: MessageToUser) {
        databaseDao.update(messaageToUser)
    }

    fun deleteMessage(message: Message) {
        val messageToUserList
                = databaseDao.getMessageToUserLiveByMessageId(message.id!!) as ArrayList<MessageToUser>

        for (i in messageToUserList) {
            databaseDao.delete(i)
        }

        databaseDao.delete(message)
    }

    fun updateMessage(message: Message) {
        databaseDao.update(message)
    }

    fun getMessageById(messageId: Long) = databaseDao.getMessageById(messageId)

    fun getMessageToUserById(id: Long) = databaseDao.getMessageToUserById(id)
}