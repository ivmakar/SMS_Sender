package ru.example.ivan.smssender.ui.screens.new_group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import ru.example.ivan.smssender.data.dbmodels.Group
import ru.example.ivan.smssender.data.repositories.ContactRepository
import ru.example.ivan.smssender.data.repositories.GroupRepository
import ru.example.ivan.smssender.ui.uimodels.Contact
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import ru.example.ivan.smssender.utility.extensions.plusAssign
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class NewGroupViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val groupRepository: GroupRepository,
    private val applicationContext: Context) : ViewModel() {

    private var _navigateComplete = SingleLiveEvent<Any>()
    val navigateComplete: LiveData<Any>
        get() = _navigateComplete

    private var _navigateAddContacts = SingleLiveEvent<Any>()
    val navigateAddContacts: LiveData<Any>
        get() = _navigateAddContacts

    var groupName = ObservableField<String>()

    val isLoading = ObservableBoolean()
    val hasContacts = ObservableBoolean(false)

    var contacts = contactRepository.getAllContacts()
    var selectedContacts = MutableLiveData<Set<Int>>()

    var groups: LiveData<List<Group>> = groupRepository.getAllGroups()

    private fun isGroupsContainsName(name: String): Boolean {
        if (groups.value.isNullOrEmpty())
            return false
        for (i in groups.value!!) {
            if (i.groupName.equals(name))
                return true
        }
        return false
    }


    fun newGroupFabCompleteOnClick() {

        //TODO: change communication between fragments on broadcast receiver
        if (selectedContacts.value.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "Добавьте хотя бы один контакт", Toast.LENGTH_LONG).show()
            return
        }
        if (groupName.get().isNullOrEmpty()){
            Toast.makeText(applicationContext, "Введите название группы", Toast.LENGTH_LONG).show()
            return
        }
        if (isGroupsContainsName(groupName.get()!!)) {
            Toast.makeText(applicationContext, "Группа с таким названием уже существует", Toast.LENGTH_LONG).show()
            return
        }

        var groupContacts = ArrayList<Contact>()
        for (i in selectedContacts.value!!) {
            contacts.value?.get(i).let { it?.let { it1 -> groupContacts.add(it1) } }
        }

        groupRepository.saveGroup(Group(null, groupName.get()!!, groupContacts.size), groupContacts)

        groupName.set("")
        selectedContacts.value = sortedSetOf()

        Toast.makeText(applicationContext, "Сохранено", Toast.LENGTH_LONG).show()

        _navigateComplete.call()
    }


    fun newGroupAddContactsOnClick() {
        _navigateAddContacts.call()
    }

    fun selectItemByPosition(position: Int){
        contacts.value!![position].isSelected = !contacts.value!![position].isSelected
        contacts.postValue(contacts.value)
        if (contacts.value!![position].isSelected){
            if (selectedContacts.value == null)
                selectedContacts.value = sortedSetOf()

            selectedContacts.value = selectedContacts.value!!.plus(position)
        } else {

            selectedContacts.value = selectedContacts.value!!.minus(position)
        }

    }

    fun deleteItemByPosition(position: Int){
        contacts.value!![selectedContacts.value!!.elementAt(position)].isSelected = !contacts.value!![selectedContacts.value!!.elementAt(position)].isSelected
        contacts.postValue(contacts.value)
        if (contacts.value!![selectedContacts.value!!.elementAt(position)].isSelected){
            if (selectedContacts.value == null)
                selectedContacts.value = sortedSetOf()

            selectedContacts.value = selectedContacts.value!!.plus(selectedContacts.value!!.elementAt(position))
        } else {

            selectedContacts.value = selectedContacts.value!!.minus(selectedContacts.value!!.elementAt(position))
        }

    }
    //TODO: get droupName.messageText
}