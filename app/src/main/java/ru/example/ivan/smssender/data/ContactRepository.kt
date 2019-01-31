package ru.example.ivan.smssender.data

import io.reactivex.Observable
import ru.example.ivan.smssender.ui.uimodels.Contact
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ContactRepository @Inject constructor() {

    fun getContacts() : Observable<ArrayList<Contact>> {
        var arrayList = ArrayList<Contact>()
        arrayList.add(Contact(1, "Иван Макаров", "+7(929)819-33-40"))
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
        arrayList.add(Contact(12, "Андрей", "+7(928)603-99-34"))

        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }
}