package ru.example.ivan.smssender.ui.screens.new_message

import android.content.Context
import android.telephony.SubscriptionInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.dbmodels.Group
import ru.example.ivan.smssender.data.dbmodels.Message
import ru.example.ivan.smssender.data.dbmodels.MessageToUser
import ru.example.ivan.smssender.data.repositories.ContactRepository
import ru.example.ivan.smssender.data.repositories.GroupRepository
import ru.example.ivan.smssender.data.repositories.MessageRepository
import ru.example.ivan.smssender.data.repositories.SimRepository
import ru.example.ivan.smssender.ui.rvadapters.SimInfoSpinnerAdapter
import ru.example.ivan.smssender.ui.uimodels.Contact
import ru.example.ivan.smssender.ui.uimodels.SimInfo
import ru.example.ivan.smssender.utility.Constants
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import ru.example.ivan.smssender.utility.extensions.plusAssign
import ru.example.ivan.smssender.utility.phone_number_parsing.AppFunctions
import ru.example.ivan.smssender.utility.send_sms.SendExecutor
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class NewMessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val groupRepository: GroupRepository,
    private val contactRepository: ContactRepository,
    private val simRepository: SimRepository,
    private val applicationContext: Context,
    private val sendExecutor: SendExecutor) : ViewModel() {

    private var _navigateComplete = SingleLiveEvent<Any>()
    val navigateComplete: LiveData<Any>
        get() = _navigateComplete

    var group = Group(null, "", 0) //TODO:

    var groupContactList = ArrayList<Contact>()

    var isRandomInterval = ObservableBoolean(false)
    var isScheduleSending = ObservableBoolean(false)

    var intervalStart = ObservableInt(1)
    var intarvalEnd = ObservableInt(10)

    var scheduleDate: Calendar = Calendar.getInstance()
    var scheduleDateText = ObservableField<String>()

    var messageText = ObservableField<String>()

    var maxSimb = ObservableInt(160)
    var curSimb = ObservableInt(0)
    var curMessageCount = ObservableInt(1)

    var messageToUserList = ArrayList<MessageToUser>()

    var simInfoList = MutableLiveData<ArrayList<SimInfo>>()

    var selectedSimPosition: Int? = null
    lateinit var simAdapter: SimInfoSpinnerAdapter

    private var compositeDisposable = CompositeDisposable()

    init {
        loadSimInfo()
    }

    fun loadGroup(groupId: Long){
        compositeDisposable += groupRepository
            .getGroupById(groupId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<Group>() {


                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: Group) {
                    group = t
                }

                override fun onComplete() {
                    loadContactsByGroupId(group.id!!)
                }

            })
    }

    fun loadContactsByGroupId(groupId: Long){
        compositeDisposable += contactRepository
            .getContactsByGroupId(groupId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<ArrayList<Contact>>() {


                override fun onError(e: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onNext(t: ArrayList<Contact>) {

                    groupContactList = t
                }

                override fun onComplete() {
                }

            })
    }

    fun loadSimInfo(){
        val list = simRepository.getSubscriptionList() as ArrayList<SubscriptionInfo>

        simInfoList.value = ArrayList<SimInfo>()

        for (i in list) {
            val simInfo = SimInfo(
                i.createIconBitmap(applicationContext),
                i.displayName.toString(),
                if(i.number == null) {" "} else {i.number},
                i.subscriptionId)
            simInfoList.value?.add(simInfo)
        }
        simInfoList.value = simInfoList.value
    }

    fun onMessageTextChanged(s: Editable) {
        messageText.set(s.toString())
        if (messageText.get().isNullOrEmpty()){
            maxSimb.set(160)
            curSimb.set(0)
            curMessageCount.set(1)
            return
        }
        curSimb.set(messageText.get()!!.length)

        maxSimb.set(160)

        val str = messageText.get()!!
        for (i in str) {
            if (isRussianText(i)) {
                maxSimb.set(70)
                break
            }
        }

        if (curSimb.get() > maxSimb.get()) {
            if (maxSimb.get() == 160)
                maxSimb.set(153)
            else
                maxSimb.set(67)

        }

        curMessageCount.set(curSimb.get() / maxSimb.get() + 1)

    }

    private fun isRussianText (ch: Char): Boolean {
        if (ch in 'а'..'я' || ch in 'А'..'Я') {
            return true
        }
        return false
    }

    fun writeDate() {
        val sdf = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
        scheduleDateText.set(sdf.format(Date(scheduleDate.timeInMillis)))
    }

    private fun setMessageToUserList(sendDate: Date) {
        for (i in groupContactList) {
            messageToUserList.add(MessageToUser(null,
                0,
                AppFunctions.standartizePhoneNumber(i.phoneNumber),
                sendDate.time,
                Constants.STATUS_SENDED,
                if(selectedSimPosition != null) {simAdapter.getItem(selectedSimPosition!!).simName} else {Constants.NO_SIM},
                if(selectedSimPosition != null) {simAdapter.getItem(selectedSimPosition!!).subId} else {0}))
        }
    }

    fun saveNewMessage() {
        //TODO: analize input fields
        val sendDate = Date()
        val message = Message(null, group.id!!, messageText.get()!!, "send", sendDate.time, Constants.STATUS_SENDED, isScheduleSending.get()!!)

        setMessageToUserList(sendDate)
        message.id = messageRepository.saveMessage(message, messageToUserList)

        sendExecutor.initSending(message)
    }

    fun sendOnClick() {
        _navigateComplete.call()
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }

}