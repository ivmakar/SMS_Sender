package ru.example.ivan.smssender.data.dbmodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.databinding.BaseObservable

@Entity
class Template (
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var name: String,
    var templateText: String) : BaseObservable()