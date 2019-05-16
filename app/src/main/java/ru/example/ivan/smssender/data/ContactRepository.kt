package ru.example.ivan.smssender.data

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.ContactsContract
import io.reactivex.Observable
import ru.example.ivan.smssender.ui.uimodels.Contact
import javax.inject.Inject


class ContactRepository @Inject constructor() {

    fun getContacts(contentResolver: ContentResolver) : Observable<ArrayList<Contact>> {
        var contactsList = ArrayList<Contact>()

        contactsList = readContactsFromPhone(contentResolver)
        return Observable.just(contactsList)

//        var arrayList = ArrayList<Contact>()
/*        arrayList.add(Contact(1, "Иван Макаров", "+7(929)819-33-40"))
        arrayList.add(Contact(2, "Роман Макаров", "+7(928)195-21-45"))
        arrayList.add(Contact(3, "Андрей Макаров", "+7(928)603-99-34"))
        arrayList.add(Contact(4, "Маша Макарова", "+7(929)819-33-41"))
        arrayList.add(Contact(5, "Ваня Макаров", "+7(988)547-08-67"))
        arrayList.add(Contact(6, "Андрей", "+7(928)603-99-34"))
        arrayList.add(Contact(7, "Иван Макаров", "+7(929)819-33-40"))
        arrayList.add(Contact(8, "Роман Макаров", "+7(928)195-21-45"))
        arrayList.add(Contact(9, "Андрей Макаров", "+7(928)603-99-34"))
        arrayList.add(Contact(10, "Маша Макарова", "+7(929)819-33-41"))
        arrayList.add(Contact(11, "Ваня Макаров", "+7(988)547-08-67"))
        arrayList.add(Contact(12, "Андрей", "+7(928)603-99-34"))*/
/*
        arrayList.add(Contact(0, "Контакт 0", "+7(929)928-600-00"))
        arrayList.add(Contact(1, "Контакт 1", "+7(929)928-600-01"))
        arrayList.add(Contact(2, "Контакт 2", "+7(929)928-600-02"))
        arrayList.add(Contact(3, "Контакт 3", "+7(929)928-600-03"))
        arrayList.add(Contact(4, "Контакт 4", "+7(929)928-600-04"))
        arrayList.add(Contact(5, "Контакт 5", "+7(929)928-600-05"))
        arrayList.add(Contact(6, "Контакт 6", "+7(929)928-600-06"))
        arrayList.add(Contact(7, "Контакт 7", "+7(929)928-600-07"))
        arrayList.add(Contact(8, "Контакт 8", "+7(929)928-600-08"))
        arrayList.add(Contact(9, "Контакт 9", "+7(929)928-600-09"))
        arrayList.add(Contact(10, "Контакт 10", "+7(929)928-600-10"))
        arrayList.add(Contact(11, "Контакт 11", "+7(929)928-600-11"))
        arrayList.add(Contact(12, "Контакт 12", "+7(929)928-600-12"))
        arrayList.add(Contact(13, "Контакт 13", "+7(929)928-600-13"))
        arrayList.add(Contact(14, "Контакт 14", "+7(929)928-600-14"))
        arrayList.add(Contact(15, "Контакт 15", "+7(929)928-600-15"))
        arrayList.add(Contact(16, "Контакт 16", "+7(929)928-600-16"))
        arrayList.add(Contact(17, "Контакт 17", "+7(929)928-600-17"))
        arrayList.add(Contact(18, "Контакт 18", "+7(929)928-600-18"))
        arrayList.add(Contact(19, "Контакт 19", "+7(929)928-600-19"))
        arrayList.add(Contact(20, "Контакт 20", "+7(929)928-600-20"))
        arrayList.add(Contact(21, "Контакт 21", "+7(929)928-600-21"))
        arrayList.add(Contact(22, "Контакт 22", "+7(929)928-600-22"))
        arrayList.add(Contact(23, "Контакт 23", "+7(929)928-600-23"))
        arrayList.add(Contact(24, "Контакт 24", "+7(929)928-600-24"))
        arrayList.add(Contact(25, "Контакт 25", "+7(929)928-600-25"))
        arrayList.add(Contact(26, "Контакт 26", "+7(929)928-600-26"))
        arrayList.add(Contact(27, "Контакт 27", "+7(929)928-600-27"))
        arrayList.add(Contact(28, "Контакт 28", "+7(929)928-600-28"))
        arrayList.add(Contact(29, "Контакт 29", "+7(929)928-600-29"))
        arrayList.add(Contact(30, "Контакт 30", "+7(929)928-600-30"))
        arrayList.add(Contact(31, "Контакт 31", "+7(929)928-600-31"))
        arrayList.add(Contact(32, "Контакт 32", "+7(929)928-600-32"))
        arrayList.add(Contact(33, "Контакт 33", "+7(929)928-600-33"))
        arrayList.add(Contact(34, "Контакт 34", "+7(929)928-600-34"))
        arrayList.add(Contact(35, "Контакт 35", "+7(929)928-600-35"))
        arrayList.add(Contact(36, "Контакт 36", "+7(929)928-600-36"))
        arrayList.add(Contact(37, "Контакт 37", "+7(929)928-600-37"))
        arrayList.add(Contact(38, "Контакт 38", "+7(929)928-600-38"))
        arrayList.add(Contact(39, "Контакт 39", "+7(929)928-600-39"))
        arrayList.add(Contact(40, "Контакт 40", "+7(929)928-600-40"))


        arrayList.add(Contact(0, "Контакт 0", "+7(929)928-600-00"))
        arrayList.add(Contact(1, "Контакт 1", "+7(929)928-600-01"))
        arrayList.add(Contact(2, "Контакт 2", "+7(929)928-600-02"))
        arrayList.add(Contact(3, "Контакт 3", "+7(929)928-600-03"))
        arrayList.add(Contact(4, "Контакт 4", "+7(929)928-600-04"))
        arrayList.add(Contact(5, "Контакт 5", "+7(929)928-600-05"))
        arrayList.add(Contact(6, "Контакт 6", "+7(929)928-600-06"))
        arrayList.add(Contact(7, "Контакт 7", "+7(929)928-600-07"))
        arrayList.add(Contact(8, "Контакт 8", "+7(929)928-600-08"))
        arrayList.add(Contact(9, "Контакт 9", "+7(929)928-600-09"))
        arrayList.add(Contact(10, "Контакт 10", "+7(929)928-600-10"))
        arrayList.add(Contact(11, "Контакт 11", "+7(929)928-600-11"))
        arrayList.add(Contact(12, "Контакт 12", "+7(929)928-600-12"))
        arrayList.add(Contact(13, "Контакт 13", "+7(929)928-600-13"))
        arrayList.add(Contact(14, "Контакт 14", "+7(929)928-600-14"))
        arrayList.add(Contact(15, "Контакт 15", "+7(929)928-600-15"))
        arrayList.add(Contact(16, "Контакт 16", "+7(929)928-600-16"))
        arrayList.add(Contact(17, "Контакт 17", "+7(929)928-600-17"))
        arrayList.add(Contact(18, "Контакт 18", "+7(929)928-600-18"))
        arrayList.add(Contact(19, "Контакт 19", "+7(929)928-600-19"))
        arrayList.add(Contact(20, "Контакт 20", "+7(929)928-600-20"))
        arrayList.add(Contact(21, "Контакт 21", "+7(929)928-600-21"))
        arrayList.add(Contact(22, "Контакт 22", "+7(929)928-600-22"))
        arrayList.add(Contact(23, "Контакт 23", "+7(929)928-600-23"))
        arrayList.add(Contact(24, "Контакт 24", "+7(929)928-600-24"))
        arrayList.add(Contact(25, "Контакт 25", "+7(929)928-600-25"))
        arrayList.add(Contact(26, "Контакт 26", "+7(929)928-600-26"))
        arrayList.add(Contact(27, "Контакт 27", "+7(929)928-600-27"))
        arrayList.add(Contact(28, "Контакт 28", "+7(929)928-600-28"))
        arrayList.add(Contact(29, "Контакт 29", "+7(929)928-600-29"))
        arrayList.add(Contact(30, "Контакт 30", "+7(929)928-600-30"))
        arrayList.add(Contact(31, "Контакт 31", "+7(929)928-600-31"))
        arrayList.add(Contact(32, "Контакт 32", "+7(929)928-600-32"))
        arrayList.add(Contact(33, "Контакт 33", "+7(929)928-600-33"))
        arrayList.add(Contact(34, "Контакт 34", "+7(929)928-600-34"))
        arrayList.add(Contact(35, "Контакт 35", "+7(929)928-600-35"))
        arrayList.add(Contact(36, "Контакт 36", "+7(929)928-600-36"))
        arrayList.add(Contact(37, "Контакт 37", "+7(929)928-600-37"))
        arrayList.add(Contact(38, "Контакт 38", "+7(929)928-600-38"))
        arrayList.add(Contact(39, "Контакт 39", "+7(929)928-600-39"))
        arrayList.add(Contact(40, "Контакт 40", "+7(929)928-600-40"))
        arrayList.add(Contact(0, "Контакт 0", "+7(929)928-600-00"))
        arrayList.add(Contact(1, "Контакт 1", "+7(929)928-600-01"))
        arrayList.add(Contact(2, "Контакт 2", "+7(929)928-600-02"))
        arrayList.add(Contact(3, "Контакт 3", "+7(929)928-600-03"))
        arrayList.add(Contact(4, "Контакт 4", "+7(929)928-600-04"))
        arrayList.add(Contact(5, "Контакт 5", "+7(929)928-600-05"))
        arrayList.add(Contact(6, "Контакт 6", "+7(929)928-600-06"))
        arrayList.add(Contact(7, "Контакт 7", "+7(929)928-600-07"))
        arrayList.add(Contact(8, "Контакт 8", "+7(929)928-600-08"))
        arrayList.add(Contact(9, "Контакт 9", "+7(929)928-600-09"))
        arrayList.add(Contact(10, "Контакт 10", "+7(929)928-600-10"))
        arrayList.add(Contact(11, "Контакт 11", "+7(929)928-600-11"))
        arrayList.add(Contact(12, "Контакт 12", "+7(929)928-600-12"))
        arrayList.add(Contact(13, "Контакт 13", "+7(929)928-600-13"))
        arrayList.add(Contact(14, "Контакт 14", "+7(929)928-600-14"))
        arrayList.add(Contact(15, "Контакт 15", "+7(929)928-600-15"))
        arrayList.add(Contact(16, "Контакт 16", "+7(929)928-600-16"))
        arrayList.add(Contact(17, "Контакт 17", "+7(929)928-600-17"))
        arrayList.add(Contact(18, "Контакт 18", "+7(929)928-600-18"))
        arrayList.add(Contact(19, "Контакт 19", "+7(929)928-600-19"))
        arrayList.add(Contact(20, "Контакт 20", "+7(929)928-600-20"))
        arrayList.add(Contact(21, "Контакт 21", "+7(929)928-600-21"))
        arrayList.add(Contact(22, "Контакт 22", "+7(929)928-600-22"))
        arrayList.add(Contact(23, "Контакт 23", "+7(929)928-600-23"))
        arrayList.add(Contact(24, "Контакт 24", "+7(929)928-600-24"))
        arrayList.add(Contact(25, "Контакт 25", "+7(929)928-600-25"))
        arrayList.add(Contact(26, "Контакт 26", "+7(929)928-600-26"))
        arrayList.add(Contact(27, "Контакт 27", "+7(929)928-600-27"))
        arrayList.add(Contact(28, "Контакт 28", "+7(929)928-600-28"))
        arrayList.add(Contact(29, "Контакт 29", "+7(929)928-600-29"))
        arrayList.add(Contact(30, "Контакт 30", "+7(929)928-600-30"))
        arrayList.add(Contact(31, "Контакт 31", "+7(929)928-600-31"))
        arrayList.add(Contact(32, "Контакт 32", "+7(929)928-600-32"))
        arrayList.add(Contact(33, "Контакт 33", "+7(929)928-600-33"))
        arrayList.add(Contact(34, "Контакт 34", "+7(929)928-600-34"))
        arrayList.add(Contact(35, "Контакт 35", "+7(929)928-600-35"))
        arrayList.add(Contact(36, "Контакт 36", "+7(929)928-600-36"))
        arrayList.add(Contact(37, "Контакт 37", "+7(929)928-600-37"))
        arrayList.add(Contact(38, "Контакт 38", "+7(929)928-600-38"))
        arrayList.add(Contact(39, "Контакт 39", "+7(929)928-600-39"))
        arrayList.add(Contact(40, "Контакт 40", "+7(929)928-600-40"))
        arrayList.add(Contact(0, "Контакт 0", "+7(929)928-600-00"))
        arrayList.add(Contact(1, "Контакт 1", "+7(929)928-600-01"))
        arrayList.add(Contact(2, "Контакт 2", "+7(929)928-600-02"))
        arrayList.add(Contact(3, "Контакт 3", "+7(929)928-600-03"))
        arrayList.add(Contact(4, "Контакт 4", "+7(929)928-600-04"))
        arrayList.add(Contact(5, "Контакт 5", "+7(929)928-600-05"))
        arrayList.add(Contact(6, "Контакт 6", "+7(929)928-600-06"))
        arrayList.add(Contact(7, "Контакт 7", "+7(929)928-600-07"))
        arrayList.add(Contact(8, "Контакт 8", "+7(929)928-600-08"))
        arrayList.add(Contact(9, "Контакт 9", "+7(929)928-600-09"))
        arrayList.add(Contact(10, "Контакт 10", "+7(929)928-600-10"))
        arrayList.add(Contact(11, "Контакт 11", "+7(929)928-600-11"))
        arrayList.add(Contact(12, "Контакт 12", "+7(929)928-600-12"))
        arrayList.add(Contact(13, "Контакт 13", "+7(929)928-600-13"))
        arrayList.add(Contact(14, "Контакт 14", "+7(929)928-600-14"))
        arrayList.add(Contact(15, "Контакт 15", "+7(929)928-600-15"))
        arrayList.add(Contact(16, "Контакт 16", "+7(929)928-600-16"))
        arrayList.add(Contact(17, "Контакт 17", "+7(929)928-600-17"))
        arrayList.add(Contact(18, "Контакт 18", "+7(929)928-600-18"))
        arrayList.add(Contact(19, "Контакт 19", "+7(929)928-600-19"))
        arrayList.add(Contact(20, "Контакт 20", "+7(929)928-600-20"))
        arrayList.add(Contact(21, "Контакт 21", "+7(929)928-600-21"))
        arrayList.add(Contact(22, "Контакт 22", "+7(929)928-600-22"))
        arrayList.add(Contact(23, "Контакт 23", "+7(929)928-600-23"))
        arrayList.add(Contact(24, "Контакт 24", "+7(929)928-600-24"))
        arrayList.add(Contact(25, "Контакт 25", "+7(929)928-600-25"))
        arrayList.add(Contact(26, "Контакт 26", "+7(929)928-600-26"))
        arrayList.add(Contact(27, "Контакт 27", "+7(929)928-600-27"))
        arrayList.add(Contact(28, "Контакт 28", "+7(929)928-600-28"))
        arrayList.add(Contact(29, "Контакт 29", "+7(929)928-600-29"))
        arrayList.add(Contact(30, "Контакт 30", "+7(929)928-600-30"))
        arrayList.add(Contact(31, "Контакт 31", "+7(929)928-600-31"))
        arrayList.add(Contact(32, "Контакт 32", "+7(929)928-600-32"))
        arrayList.add(Contact(33, "Контакт 33", "+7(929)928-600-33"))
        arrayList.add(Contact(34, "Контакт 34", "+7(929)928-600-34"))
        arrayList.add(Contact(35, "Контакт 35", "+7(929)928-600-35"))
        arrayList.add(Contact(36, "Контакт 36", "+7(929)928-600-36"))
        arrayList.add(Contact(37, "Контакт 37", "+7(929)928-600-37"))
        arrayList.add(Contact(38, "Контакт 38", "+7(929)928-600-38"))
        arrayList.add(Contact(39, "Контакт 39", "+7(929)928-600-39"))
        arrayList.add(Contact(40, "Контакт 40", "+7(929)928-600-40"))
*/
//        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }

    private fun readContactsFromPhone(contentResolver: ContentResolver): ArrayList<Contact> {

        val CONTENT_URI = ContactsContract.Contacts.CONTENT_URI
        val _ID = ContactsContract.Contacts._ID
        val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
        val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER

        val PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER

        val queryColumnArr = arrayOf(_ID, DISPLAY_NAME, HAS_PHONE_NUMBER)
        val cursor = contentResolver.query(CONTENT_URI, queryColumnArr, null, null, null)

        var contactsArray = ArrayList<Contact>()

        var id = 1
        if (cursor != null) {
            cursor.moveToFirst()
            do {
                val contact_id = cursor.getString(cursor.getColumnIndex(_ID))
                val name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
                val hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)))

                //Получаем имя:
                if (hasPhoneNumber > 0) {

                    val phoneCursor = contentResolver.query(PhoneCONTENT_URI, arrayOf(NUMBER),
                            Phone_CONTACT_ID + " = ?", arrayOf(contact_id), null)

                    if (phoneCursor.moveToFirst()) {
                        val phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER))
                        phoneCursor.close()
                        contactsArray.add(Contact(id, name, phoneNumber, false))
                    }
                }
                id++
            } while (cursor.moveToNext())
        }


        return contactsArray
    }
}