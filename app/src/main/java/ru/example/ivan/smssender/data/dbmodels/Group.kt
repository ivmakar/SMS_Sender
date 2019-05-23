package ru.example.ivan.smssender.data.dbmodels

import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Group(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val groupName: String,
    var memberUsers: Int = 0) : BaseObservable()