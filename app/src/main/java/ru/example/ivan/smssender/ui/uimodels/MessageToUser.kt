package ru.example.ivan.smssender.ui.uimodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions
import java.sql.Date

@Entity
class MessageToUser (
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val messageId: Long,
    phoneNumber: String,
    val sendDate: Date,
    val status: String,
    val isSending: Boolean
) {
    val userPhoneNumber: String = AppFunctions.standartizePhoneNumber(phoneNumber)
}