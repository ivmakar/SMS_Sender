package ru.example.ivan.smssender.utility.roomdb

import androidx.room.RoomDatabase
import androidx.room.Database
import ru.example.ivan.smssender.data.dbmodels.Message
import ru.example.ivan.smssender.data.dbmodels.MessageToUser
import ru.example.ivan.smssender.data.dbmodels.Template
import ru.example.ivan.smssender.data.dbmodels.UserToGroup


@Database(
    entities = [
        Message::class,
        Template::class,
        MessageToUser::class,
        UserToGroup::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao
}