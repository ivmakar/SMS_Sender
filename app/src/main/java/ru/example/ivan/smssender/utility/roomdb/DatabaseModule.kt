package ru.example.ivan.smssender.utility.roomdb

import androidx.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, "smssender-db").allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun providesDatabaseDao(appDatabase: AppDatabase): DatabaseDao {
        return appDatabase.databaseDao()
    }


}