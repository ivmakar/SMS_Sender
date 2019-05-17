package ru.example.ivan.smssender.ui.uimodels

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable

@Entity
class Message(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var text: String?,
    var date: String?,
    var srtype: String = "send") : BaseObservable()