package ru.example.ivan.smssender

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.example.ivan.smssender.utility.roomdb.AppDatabase
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun providesContext(application: App) : Context {
        return application.applicationContext
    }
}