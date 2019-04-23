package ru.example.ivan.smssender

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.example.ivan.smssender.ui.screens.chain.ChainViewModule
import ru.example.ivan.smssender.ui.screens.group.GroupFragmentModule
import ru.example.ivan.smssender.ui.screens.main.MainActivityModule
import ru.example.ivan.smssender.ui.screens.messages.MessagesFragmentModule
import ru.example.ivan.smssender.ui.screens.new_group.NewGroupFragmentModule
import ru.example.ivan.smssender.ui.screens.new_message.NewMessageFragmentModule
import ru.example.ivan.smssender.utility.di.ViewModelBuilder
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelBuilder::class,
        MainActivityModule::class,
        GroupFragmentModule::class,
        NewGroupFragmentModule::class,
        MessagesFragmentModule::class,
        ChainViewModule::class,
        NewMessageFragmentModule::class])

interface AppComponent : AndroidInjector<App> {


    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}