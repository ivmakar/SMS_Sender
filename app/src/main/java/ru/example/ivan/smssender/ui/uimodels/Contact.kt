package ru.example.ivan.smssender.ui.uimodels

import android.databinding.BaseObservable
import java.io.Serializable

class Contact(var id: Int?, var name: String?, var number: String?, var isSelected: Boolean = false) : BaseObservable(), Serializable