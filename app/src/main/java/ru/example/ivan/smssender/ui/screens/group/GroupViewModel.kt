package ru.example.ivan.smssender.ui.screens.group

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import ru.example.ivan.smssender.data.repositories.GroupRepository
import ru.example.ivan.smssender.data.dbmodels.Group
import ru.example.ivan.smssender.utility.extensions.SingleLiveEvent
import javax.inject.Inject

class GroupViewModel @Inject constructor(private var groupRepository: GroupRepository) : ViewModel() {

    private var _navigateToNewGroup = SingleLiveEvent<Any>()
    val navigateToNewGroup: LiveData<Any>
        get() = _navigateToNewGroup

    val isLoading = ObservableBoolean()

    var groups: LiveData<List<Group>> = groupRepository.getAllGroups()


    fun groupOnClick() {
        _navigateToNewGroup.call()
    }

    fun getGroupByPosition (position: Int) = groups.value?.get(position)

}