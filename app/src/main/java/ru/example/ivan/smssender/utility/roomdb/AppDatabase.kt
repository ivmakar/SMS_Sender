package ru.example.ivan.smssender.utility.roomdb

import androidx.room.RoomDatabase
import androidx.room.Database
import ru.example.ivan.smssender.ui.uimodels.Message
import ru.example.ivan.smssender.ui.uimodels.Template


@Database(
    entities = [Message::class, Template::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao
}