package ru.example.ivan.smssender.ui.uimodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.databinding.BaseObservable

@Entity
class Template (
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var name: String,
    var templateText: String) : BaseObservable()