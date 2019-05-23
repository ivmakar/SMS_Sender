package ru.example.ivan.smssender.data.dbmodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions
import java.sql.Date

@Entity
class MessageToUser (
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var messageId: Long,
    val userPhoneNumber: String,
    val sendDate: Long,
    var status: String,
    val sim: String,
    val subId: Int,
    val interval: Int
)