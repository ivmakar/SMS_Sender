package ru.example.ivan.smssender.ui.screens.messages

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey

@Module
internal abstract class MessagesFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun messagesFragment(): MessagesFragment

    @Binds
    @IntoMap
    @ViewModelKey(MessagesViewModel::class)
    abstract fun bindMessagesViewModel(viewModel: MessagesViewModel): ViewModel


}
