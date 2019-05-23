package ru.example.ivan.smssender.utility.send_sms

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.example.ivan.smssender.data.repositories.MessageRepository
import javax.inject.Singleton

@Module
class MessageStatusReceiverModule {

    @ContributesAndroidInjector
    fun providesMessageStatusReceiver(messageRepository: MessageRepository): MessageStatusReceiver {
        return MessageStatusReceiver(messageRepository)
    }
}