package ru.example.ivan.smssender.ui.uimodels

import android.databinding.BaseObservable

class Group(var groupName: String?, var memberUsers: Int = 0) : BaseObservable()