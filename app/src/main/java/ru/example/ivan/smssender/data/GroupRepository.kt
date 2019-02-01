package ru.example.ivan.smssender.data

import io.reactivex.Observable
import ru.example.ivan.smssender.ui.uimodels.Group
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class GroupRepository @Inject constructor() {

    fun getGroups() : Observable<ArrayList<Group>> {
        var arrayList = ArrayList<Group>()
        arrayList.add(Group(1, "Молодежь", 45))
        arrayList.add(Group(2, "Группа НГ", 25))
        arrayList.add(Group(3, "Братья", 30))

        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }
}