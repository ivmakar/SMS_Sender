package ru.example.ivan.smssender.ui.screens.message_details

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey

@Module
internal abstract class MessageDetailsFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun messageDetailsFragment(): MessageDetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(MessageDetailsViewModel::class)
    abstract fun bindMessageDetailsViewModel(viewModel: MessageDetailsViewModel): ViewModel


}
