package ru.example.ivan.smssender.ui.uimodels

import androidx.databinding.BaseObservable

class Group(var id: Int, var groupName: String?, var memberUsers: Int = 0) : BaseObservable()