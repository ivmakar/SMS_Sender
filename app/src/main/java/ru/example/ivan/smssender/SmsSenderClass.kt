package ru.example.ivan.smssender

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SMSSender : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

}