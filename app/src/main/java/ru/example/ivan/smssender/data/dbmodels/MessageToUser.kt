package ru.example.ivan.smssender.data.dbmodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions
import java.sql.Date

@Entity
class MessageToUser (
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val messageId: Long,
    val userPhoneNumber: String,
    val sendDate: Long,
    val status: String,
    val isSending: Boolean
)