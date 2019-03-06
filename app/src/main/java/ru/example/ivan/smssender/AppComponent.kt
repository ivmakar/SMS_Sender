package ru.example.ivan.smssender

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.example.ivan.smssender.ui.screens.chain.ChainViewModule
import ru.example.ivan.smssender.ui.screens.contacts.ContactActivityModule
import ru.example.ivan.smssender.ui.screens.group.GroupActivity
import ru.example.ivan.smssender.ui.screens.group.GroupActivityModule
import ru.example.ivan.smssender.ui.screens.main.MainActivityModule
import ru.example.ivan.smssender.ui.screens.messages.MessagesActivityModule
import ru.example.ivan.smssender.ui.screens.new_group.NewGroupActivityModule
import ru.example.ivan.smssender.utility.di.ViewModelBuilder
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelBuilder::class,
        MainActivityModule::class,
        GroupActivityModule::class,
        NewGroupActivityModule::class,
        ContactActivityModule::class,
        MessagesActivityModule::class,
        ChainViewModule::class])

interface AppComponent : AndroidInjector<App> {


    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}