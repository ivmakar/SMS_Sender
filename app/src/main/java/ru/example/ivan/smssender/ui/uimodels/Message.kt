package ru.example.ivan.smssender.ui.uimodels

import android.databinding.BaseObservable

class Message(var id: Int?, var text: String?, var date: String?, var srtype: String = "send")  : BaseObservable()