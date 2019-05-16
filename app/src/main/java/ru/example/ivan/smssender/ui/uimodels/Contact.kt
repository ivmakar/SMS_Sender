package ru.example.ivan.smssender.ui.uimodels

import android.databinding.BaseObservable
import android.os.Parcel
import android.os.Parcelable

class Contact(var id: Int = -1, var name: String?, var number: String?, var isSelected: Boolean = false) : BaseObservable(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(number)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other?.hashCode() ?: false
    }

    override fun hashCode(): Int {
        return id
    }
}