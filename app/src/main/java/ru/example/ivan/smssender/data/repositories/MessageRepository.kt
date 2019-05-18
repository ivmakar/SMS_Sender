package ru.example.ivan.smssender.data.repositories

import io.reactivex.Observable
import ru.example.ivan.smssender.data.dbmodels.Message
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MessageRepository @Inject constructor() {

    fun getMessages(groupId: Any): Observable<ArrayList<Message>> {
        var arrayList = ArrayList<Message>()
/*        arrayList.add(
            Message(
                1,
                "Молодежь! Сегодня будет Месяц Молитвы, не забывайте открытки и по 100 рублей!",
                "12.12.2019"
            )
        )
        arrayList.add(Message(2, "Собираемся сегодня на ДМ в 12:00", "12:12"))
        arrayList.add(
            Message(
                3,
                "В пятницу будет брацкое общение, посарайтесь не опаздывать.",
                "12.12.2019 12:12"
            )
        )*/

        return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }
}