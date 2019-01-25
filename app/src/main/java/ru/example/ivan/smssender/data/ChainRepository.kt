package ru.example.ivan.smssender.data

import android.os.Handler
import ru.example.ivan.smssender.ui.uimodels.Chain

class ChainRepository {

    fun getChains(onChainsReadyCallback: OnChainsReadyCallback) {
        var arrayList = ArrayList<Chain>()
        arrayList.add(Chain("Молодежь", "Молодежь! Сегодня будет Месяц Молитвы, не забывайте открытки и по 100 рублей!", 100 , "12.12.2019 12:12"))
        arrayList.add(Chain("Группа НГ", "Собираемся сегодня на ДМ в 12:00", 30 , "12:12"))
        arrayList.add(Chain("Братья", "В пятницу будет брацкое общение, посарайтесь не опаздывать.", 430 , "12.12.2019 12:12"))

        Handler().postDelayed({ onChainsReadyCallback.onDataReady(arrayList) },2000)
    }
}

interface OnChainsReadyCallback {
    fun onDataReady(chainList : ArrayList<Chain>)
}