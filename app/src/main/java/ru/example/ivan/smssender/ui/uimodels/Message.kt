package ru.example.ivan.smssender.ui.uimodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.databinding.BaseObservable

@Entity
class Message(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var text: String?,
    var date: String?,
    var srtype: String = "send") : BaseObservable()