package ru.example.ivan.smssender

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ru.example.ivan.smssender.utility.roomdb.AppDatabase
import android.arch.persistence.room.Room



class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

}