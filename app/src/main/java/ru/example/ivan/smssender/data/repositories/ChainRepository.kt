package ru.example.ivan.smssender.data.repositories

import android.os.Handler
import io.reactivex.Observable
import ru.example.ivan.smssender.ui.uimodels.Chain
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChainRepository @Inject constructor() {

    fun getChains() : Observable<ArrayList<Chain>> {
        var arrayList = ArrayList<Chain>()
        arrayList.add(Chain(1, "Молодежь", "Молодежь! Сегодня будет Месяц Молитвы, не забывайте открытки и по 100 рублей!", 100 , "12.12.2019 12:12"))
        arrayList.add(Chain(2, "Группа НГ", "Собираемся сегодня на ДМ в 12:00", 30 , "12:12"))
        arrayList.add(Chain(3, "Братья", "В пятницу будет брацкое общение, посарайтесь не опаздывать.", 430 , "12.12.2019 12:12"))

        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }
}