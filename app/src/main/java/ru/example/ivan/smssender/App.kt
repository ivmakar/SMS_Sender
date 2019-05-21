package ru.example.ivan.smssender

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ru.example.ivan.smssender.utility.roomdb.AppDatabase
import androidx.room.Room
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import javax.inject.Inject


class App : DaggerApplication() {

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        configureWorkManager()
    }

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, config)
    }

}