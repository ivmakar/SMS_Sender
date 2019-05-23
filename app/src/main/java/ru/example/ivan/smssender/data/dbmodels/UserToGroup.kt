package ru.example.ivan.smssender.data.dbmodels


import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions

@Entity
class UserToGroup(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val groupId: Long,
    val userPhoneNumber: String
)