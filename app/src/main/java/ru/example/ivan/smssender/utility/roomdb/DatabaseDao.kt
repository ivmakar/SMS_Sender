package ru.example.ivan.smssender.utility.roomdb

import androidx.room.*
import ru.example.ivan.smssender.data.dbmodels.*


@Dao
interface DatabaseDao {

    //Message

    @Insert
    fun insert(message: Message): Long

    @Update
    fun update(message: Message)

    @Delete
    fun delete(message: Message)

    @Query("SELECT * FROM Message AS m WHERE m.id = :messageId ORDER BY sendDate")
    fun getMessageById(messageId: Long): Message

    @Query("SELECT * FROM Message AS m WHERE m.groupId = :groupId ORDER BY sendDate")
    fun getMessagesByGroupId(groupId: Long): List<Message>

    @Query("SELECT * FROM Message AS m WHERE m.groupId = :groupId ORDER BY m.sendDate")
    fun getLastMessagesByGroupId(groupId: Long): List<Message>

    @Query("SELECT COUNT(m.id) FROM Message AS m GROUP BY m.groupId HAVING m.groupId = :groupId")
    fun getMessageCountByGroupId(groupId: Long): List<Int>



    //Group

    @Insert
    fun insert(group: Group): Long

    @Update
    fun update(group: Group)

    @Delete
    fun delete(group: Group)

    @Query("SELECT * FROM 'Group' ORDER BY groupName")
    fun getGroups(): List<Group>

    @Query("SELECT * FROM `Group` AS g WHERE g.id = :groupId ORDER BY groupName")
    fun getGroupById(groupId: Long): Group



    //Template

    @Insert
    fun insert(template: Template)

    @Update
    fun update(template: Template)

    @Delete
    fun delete(template: Template)

    @Query("SELECT * FROM Template ORDER BY name")
    fun getTemplates(): List<Template>



    //MessageToUser

    @Insert
    fun insert(messageToUser: MessageToUser)

    @Update
    fun update(messageToUser: MessageToUser)

    @Delete
    fun delete(messageToUser: MessageToUser)

    @Query("SELECT * FROM MessageToUser AS mtu WHERE mtu.messageId = :messageId ORDER BY sendDate")
    fun getMessageToUserByMessageId(messageId: Long): List<MessageToUser>

    @Query("SELECT * FROM MessageToUser AS mtu WHERE mtu.id = :messageToUserId")
    fun getMessageToUserById(messageToUserId: Long): MessageToUser



    //UserToGroup

    @Insert
    fun insert(userToGroup: UserToGroup)

    @Update
    fun update(userToGroup: UserToGroup)

    @Delete
    fun delete(userToGroup: UserToGroup)

    @Query("SELECT * FROM UserToGroup AS utg WHERE utg.groupId = :groupId")
    fun getUserToGroupByGroupId(groupId: Long): List<UserToGroup>

}