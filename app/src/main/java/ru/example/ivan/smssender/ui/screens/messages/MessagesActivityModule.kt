package ru.example.ivan.smssender.ui.screens.messages

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey

@Module
internal abstract class MessagesActivityModule {

    @ContributesAndroidInjector
    internal abstract fun messagesActivity(): MessagesActivity

    @Binds
    @IntoMap
    @ViewModelKey(MessagesViewModel::class)
    abstract fun bindMessagesViewModel(viewModel: MessagesViewModel): ViewModel


}
