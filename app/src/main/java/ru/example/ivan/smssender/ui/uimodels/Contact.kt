package ru.example.ivan.smssender.ui.uimodels

import androidx.databinding.BaseObservable
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions


class Contact(
    val id: String,
    val displayName: String,
    val phoneNumber: String,
    var isSelected: Boolean = false) : BaseObservable() {

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other?.hashCode() ?: false
    }

    override fun hashCode(): Int {
        return AppFunctions.standartizePhoneNumber(phoneNumber).hashCode()
    }
}