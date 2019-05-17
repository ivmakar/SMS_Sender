package ru.example.ivan.smssender.ui.uimodels

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable

@Entity
class Template (
    @PrimaryKey(autoGenerate = true) var id: Int?,
    var name: String,
    var text: String) : BaseObservable()