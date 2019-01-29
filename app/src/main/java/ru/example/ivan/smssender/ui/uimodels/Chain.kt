package ru.example.ivan.smssender.ui.uimodels

import android.databinding.BaseObservable
import android.databinding.Bindable
import ru.example.ivan.smssender.BR

class Chain(var chainName : String?, var lastMessage: String?, var numberMessages: Int = 0
            , var lastDate: String?) : BaseObservable()