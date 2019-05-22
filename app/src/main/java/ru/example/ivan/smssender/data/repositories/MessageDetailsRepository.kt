package ru.example.ivan.smssender.data.repositories

import io.reactivex.Observable
import ru.example.ivan.smssender.ui.uimodels.MessageDetail
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions
import javax.inject.Inject

class MessageDetailsRepository @Inject constructor(
    private val contactRepository: ContactRepository,
    private val messageRepository: MessageRepository
) {

    fun getMessageDetailListByMessageId(messageId: Long): Observable<ArrayList<MessageDetail>> {
        val contacts = contactRepository.readContactsFromPhone()
        val messageToUserList = messageRepository.getMessageToUserListByMessageId(messageId)

        var messageDetailList = ArrayList<MessageDetail>()

        for (i in messageToUserList) {
            contacts.find { i.userPhoneNumber == AppFunctions.standartizePhoneNumber(it.phoneNumber) }.let {
                if (it == null) {
                    messageDetailList.add(MessageDetail(
                        AppFunctions.formatPhoneNumber(i.userPhoneNumber),
                        AppFunctions.formatPhoneNumber(i.userPhoneNumber),
                        i.status))
                } else {
                    messageDetailList.add(MessageDetail(
                        it.displayName,
                        it.phoneNumber,
                        i.status))
                }
            }
        }

        return Observable.just(messageDetailList)
    }

}