package ru.example.ivan.smssender.ui.uimodels

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import ru.example.ivan.smssender.BR

class Chain(
    val groupId: Long?,
    val chainName : String,
    val lastMessage: String,
    val numberMessages: Int = 0,
    val lastDate: String) : BaseObservable()