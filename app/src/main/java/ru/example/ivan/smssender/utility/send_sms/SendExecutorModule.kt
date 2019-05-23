package ru.example.ivan.smssender.utility.send_sms

import androidx.work.WorkerFactory
import dagger.Module
import dagger.Provides
import ru.example.ivan.smssender.data.repositories.MessageRepository
import ru.example.ivan.smssender.utility.di.DaggerWorkerFactory
import javax.inject.Singleton

@Module
class SendExecutorModule {

    @Provides
    @Singleton
    fun workerFactory(messageRepository: MessageRepository): WorkerFactory {
        return DaggerWorkerFactory(messageRepository)
    }
}