package ru.example.ivan.smssender.data.dbmodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.databinding.BaseObservable
import ru.example.ivan.smssender.utility.Constants
import java.text.SimpleDateFormat
import java.util.*

@Entity
class Message(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    val groupId: Long,
    val messageText: String,
    val srType: String,     //Not used yet ("send")
    val sendDate: Long,
    var status: String,
    var isScheduled: Boolean) : BaseObservable() {

    fun getDateFormated(): String = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault()).format(Date(sendDate))
}