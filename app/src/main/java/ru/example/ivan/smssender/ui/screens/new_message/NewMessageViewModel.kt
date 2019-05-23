package ru.example.ivan.smssender.ui.screens.new_message

import android.content.Context
import android.telephony.SubscriptionInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import android.text.Editable
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
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
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.ArrayList

class NewMessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val groupRepository: GroupRepository,
    private val contactRepository: ContactRepository,
    private val simRepository: SimRepository,
    private val applicationContext: Context) : ViewModel() {

    private var _navigateComplete = SingleLiveEvent<Any>()
    val navigateComplete: LiveData<Any>
        get() = _navigateComplete

    var group = Group(null, "", 0) //TODO:
    var groupName = ObservableField<String>("")

    var groupContactList = ArrayList<Contact>()

    var isRandomInterval = ObservableBoolean(false)
    var isScheduleSending = ObservableBoolean(false)

    var intervalStart = ObservableField<String>("5")
    var intarvalEnd = ObservableInt(10)

    var scheduleDate: Calendar = Calendar.getInstance()
    var scheduleDateText = ObservableField<String>("")

    var messageText = ObservableField<String>("")

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
                    groupName.set(group.groupName)
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

        if (curMessageCount.get() > 3) {
            messageText.set(messageText.get()!!.substring(0, maxSimb.get() * 3))
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

    private fun setMessageToUserList(sendDate: Long) {
        for (i in groupContactList) {
            messageToUserList.add(MessageToUser(null,
                0,
                AppFunctions.standartizePhoneNumber(i.phoneNumber),
                sendDate,
                Constants.STATUS_SCHEDULE,
                if(selectedSimPosition != null) {simAdapter.getItem(selectedSimPosition!!).simName} else {Constants.NO_SIM},
                if(selectedSimPosition != null) {simAdapter.getItem(selectedSimPosition!!).subId} else {0},
                intervalStart.get()?.toInt() ?: 5
            ))
        }
    }

    private fun checkInputFields(): Boolean {
        if (group.groupName.equals("")) {
            Toast.makeText(applicationContext, "Выберите группу", Toast.LENGTH_LONG).show()
            return false
        }
        if (isScheduleSending.get() && scheduleDateText.get().equals("")) {
            Toast.makeText(applicationContext, "Выберите время отправки или отмените запланированную отправку", Toast.LENGTH_LONG).show()
            return false
        }
        try {
            val interval = intervalStart.get()?.toInt()!!
            if (interval < 0 || interval > 100) {
                Toast.makeText(applicationContext, "Введите целое число не более 100 в поле 'интервал'", Toast.LENGTH_LONG).show()
                return false
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(applicationContext, "Введите целое число в поле 'интервал'", Toast.LENGTH_LONG).show()
            return false
        }
        if (messageText.get().equals("")) {
            Toast.makeText(applicationContext, "Введите текст сообщения", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun saveNewMessage(): Boolean {

        if (!checkInputFields()) {
            return false
        }

        val duration = scheduleDate.timeInMillis - Date().time
        val sendDate = if (isScheduleSending.get() && duration > 0) {
            scheduleDate.time.time
        } else {
            Date().time
        }
        val message = Message(null, group.id!!, messageText.get()!!, "send", sendDate, Constants.STATUS_SCHEDULE, isScheduleSending.get()!!)

        setMessageToUserList(sendDate)
        val messageId = messageRepository.saveMessage(message, messageToUserList)

        var mData = Data.Builder()
            .putLong(Constants.KEY_MESSAGE_ID, messageId)
            .build()

        val sendExecutorBuilder = OneTimeWorkRequestBuilder<SendExecutor>()
            .addTag("sendSms")
            .setInputData(mData)

        if (isScheduleSending.get() && duration > 0) {

            sendExecutorBuilder.setInitialDelay(duration, TimeUnit.MILLISECONDS)
        }

        val sendExecutor = sendExecutorBuilder.build()

        WorkManager.getInstance().enqueue(sendExecutor)
        return true
    }

    fun sendOnClick() {
        if (saveNewMessage()) {
            _navigateComplete.call()
        }
    }

    override fun onCleared() {
        super.onCleared()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }

}