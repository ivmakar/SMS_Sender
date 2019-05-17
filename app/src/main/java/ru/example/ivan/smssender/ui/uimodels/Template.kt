package ru.example.ivan.smssender.ui.uimodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.databinding.BaseObservable

@Entity
class Template (
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var name: String,
    var text: String) : BaseObservable()