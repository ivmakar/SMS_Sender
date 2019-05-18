package ru.example.ivan.smssender.ui.uimodels


import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions

@Entity
class UserToGroup(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val groupId: Long,
    phoneNumber: String
) {
    val userPhoneNumber: String = AppFunctions.standartizePhoneNumber(phoneNumber)
}