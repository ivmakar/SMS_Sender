package ru.example.ivan.smssender.ui.uimodels

import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Group(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val groupName: String,
    var memberUsers: Int = 0) : BaseObservable()