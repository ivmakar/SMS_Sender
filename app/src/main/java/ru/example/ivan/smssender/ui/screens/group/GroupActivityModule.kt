package ru.example.ivan.smssender.ui.screens.group

import dagger.Module
import dagger.Provides
import ru.example.ivan.smssender.data.GroupRepository

@Module
class GroupActivityModule {

    @Provides
    fun provideGroupViewModel(groupRepository: GroupRepository) : GroupViewModel =
        GroupViewModel(groupRepository)

    @Provides
    fun provideGroupRepository() : GroupRepository =
        GroupRepository()

}