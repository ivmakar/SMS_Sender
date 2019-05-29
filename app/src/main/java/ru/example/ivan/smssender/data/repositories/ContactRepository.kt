package ru.example.ivan.smssender.data.repositories

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import ru.example.ivan.smssender.data.dbmodels.UserToGroup
import ru.example.ivan.smssender.ui.uimodels.Contact
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions
import ru.example.ivan.smssender.utility.roomdb.DatabaseDao
import javax.inject.Inject


class ContactRepository @Inject constructor(private val applicationContext: Context, private val databaseDao: DatabaseDao) {

    var contactsList =  MutableLiveData<ArrayList<Contact>>()

    fun getAllContacts(): MutableLiveData<ArrayList<Contact>> {

        contactsList.value = readContactsFromPhone()
        return contactsList
    }

    fun getUserToGroupListByGroupId(groupId: Long)
            = Observable.just(getUserToGroupByGroupIdFromDb(groupId))

    fun getContactsByGroupId(groupId: Long) : Observable<ArrayList<Contact>> {
        val allContacts = if (contactsList.value.isNullOrEmpty()) {
            readContactsFromPhone()
        } else {
            contactsList.value!!
        }

        val userToGroupList = getUserToGroupByGroupIdFromDb(groupId)
        var contactsByGroup = ArrayList<Contact>()

        for (i in userToGroupList) {
            var curContact = allContacts.find {
                i.userPhoneNumber == AppFunctions.standartizePhoneNumber(it.phoneNumber)
            }
            if (curContact == null) {
                curContact = Contact("-1", AppFunctions.formatPhoneNumber(i.userPhoneNumber), AppFunctions.formatPhoneNumber(i.userPhoneNumber))
            } else {
                contactsByGroup.add(curContact)
            }


        }

        return Observable.just(contactsByGroup)
    }

    private fun getUserToGroupByGroupIdFromDb(groupId: Long) : ArrayList<UserToGroup>
            = databaseDao.getUserToGroupByGroupId(groupId) as ArrayList<UserToGroup>

    fun readContactsFromPhone(): ArrayList<Contact> {

        val CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val DISPLAY_NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        val CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
        val HAS_PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER

        val queryColumnArr = arrayOf(CONTACT_ID, DISPLAY_NAME, NUMBER, HAS_PHONE_NUMBER)
        val cursor = applicationContext.contentResolver.query(CONTENT_URI, queryColumnArr, null, null, null)

        var contactsArray = ArrayList<Contact>()

        if (cursor.moveToFirst()) {

            do {
                val contactId = cursor.getString(cursor.getColumnIndex(CONTACT_ID))
                val displayName = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
                val hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)))
                val number = cursor.getString(cursor.getColumnIndex(NUMBER))


                if (hasPhoneNumber > 0) {

                    contactsArray.add(Contact(contactId, displayName, number, false))
                }
            } while (cursor.moveToNext())
        }


        return contactsArray
    }
}