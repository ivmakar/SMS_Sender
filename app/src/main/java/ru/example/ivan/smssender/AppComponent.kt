package ru.example.ivan.smssender

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ru.example.ivan.smssender.ui.screens.group.GroupActivity
import ru.example.ivan.smssender.ui.screens.group.GroupActivityModule
import ru.example.ivan.smssender.ui.screens.main.MainActivity
import ru.example.ivan.smssender.ui.screens.main.MainActivityModule
import javax.inject.Singleton


@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        MainActivityModule::class,
        GroupActivityModule::class])

interface  AppComponent {

    fun inject(activity: MainActivity)
    fun inject(activity: GroupActivity)

}