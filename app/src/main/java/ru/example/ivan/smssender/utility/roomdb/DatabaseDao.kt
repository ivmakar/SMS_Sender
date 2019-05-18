package ru.example.ivan.smssender.utility.roomdb

import androidx.room.*
import ru.example.ivan.smssender.data.dbmodels.Message
import ru.example.ivan.smssender.data.dbmodels.MessageToUser
import ru.example.ivan.smssender.data.dbmodels.Template
import ru.example.ivan.smssender.data.dbmodels.UserToGroup


@Dao
interface DatabaseDao {

    //Message

    @Insert
    fun insert(message: Message)

    @Update
    fun update(message: Message)

    @Query("SELECT m.id, m.groupId, m.messageText, m.srType, m.sendDate, m.isScheduled FROM Message AS m WHERE m.id = :messageId")
    fun getMessageById(messageId: Long): List<Message>

    @Query("SELECT m.id, m.groupId, m.messageText, m.srType, m.sendDate, m.isScheduled FROM Message AS m WHERE m.groupId = :groupId")
    fun getMessagesByGroupId(groupId: Long): List<Message>



    //Template

    @Insert
    fun insert(template: Template)

    @Update
    fun update(template: Template)

    @Delete
    fun delete(template: Template)

    @Query("SELECT * FROM Template")
    fun getTemplates(): List<Template>



    //MessageToUser

    @Insert
    fun insert(messageToUser: MessageToUser)

    @Update
    fun update(messageToUser: MessageToUser)

    @Delete
    fun delete(messageToUser: MessageToUser)

    @Query("SELECT mtu.id, mtu.messageId, mtu.userPhoneNumber, mtu.sendDate, mtu.status, mtu.isSending FROM MessageToUser AS mtu WHERE mtu.messageId = :messageId")
    fun getMessageToUserByMessageId(messageId: Long): List<MessageToUser>



    //UserToGroup

    @Insert
    fun insert(userToGroup: UserToGroup)

    @Update
    fun update(userToGroup: UserToGroup)

    @Delete
    fun delete(userToGroup: UserToGroup)

    @Query("SELECT utg.id, utg.groupId, utg.userPhoneNumber FROM UserToGroup AS utg WHERE utg.groupId = :groupId")
    fun getUserToGroupByGroupId(groupId: Long): List<UserToGroup>

}