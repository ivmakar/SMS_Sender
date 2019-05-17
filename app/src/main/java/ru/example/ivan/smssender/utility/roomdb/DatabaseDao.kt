package ru.example.ivan.smssender.utility.roomdb

import androidx.room.Update
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.example.ivan.smssender.ui.uimodels.Message
import ru.example.ivan.smssender.ui.uimodels.Template


@Dao
interface DatabaseDao {

    @Query("SELECT * FROM Message")
    fun getMessages(): List<Message>

    @Query("SELECT * FROM Template")
    fun getTemplates(): List<Template>

    @Insert
    fun insert(message: Message)

    @Insert
    fun insert(template: Template)

/*    @get:Query("SELECT * FROM Message")
    val allMessages: List<Message>

    @get:Query("SELECT * FROM User ORDER BY User.displayName")
    val allUsers: List<User>

    @get:Query("SELECT * FROM MessageChain ORDER BY lastDate")
    val allChains: List<MessageChain>

    @get:Query("SELECT * FROM UserGroup")
    val groups: List<UserGroup>

    @Query("SELECT User.id, User.number, User.displayName FROM User, UserToGroup WHERE UserToGroup.group_id = :id AND UserToGroup.user_id = User.id")
    fun getUsersByGroupId(id: Long): List<User>

    @Query("SELECT * FROM Message WHERE chainID LIKE :chainID")
    fun getMessagesByChainID(chainID: Long): List<Message>

    @Query("SELECT UserGroup.id, UserGroup.name, UserGroup.count FROM UserGroup, MessageChain WHERE MessageChain.id = :ChainID AND UserGroup.id =  MessageChain.groupId")
    fun getGroupByChainId(ChainID: Long): UserGroup

    @Query("SELECT * FROM MessageChain WHERE groupId = :groupId")
    fun getMessagesChain(groupId: Long): MessageChain

    @Query("SELECT * FROM MessageChain WHERE id = :id")
    fun getMessagesChainById(id: Long): MessageChain

    @Query("SELECT * FROM User WHERE id LIKE :id")
    fun getUsersById(id: Long): User

    @Insert
    fun insert(message: Message)

    @Insert
    fun insert(group: UserGroup): Long

    @Insert
    fun insert(messageChain: MessageChain)

    @Insert
    fun insert(user: User)

    @Insert
    fun insert(userToGroup: UserToGroup)

    @Update
    fun update(messageChain: MessageChain)
*/
}