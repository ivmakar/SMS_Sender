package ru.example.ivan.smssender.ui.uimodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.databinding.BaseObservable
import java.sql.Date

@Entity
class Message(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    val groupId: Long,
    val messageText: String,
    val srType: String = "send",
    val sendDate: Date,
    var isScheduled: Boolean) : BaseObservable()