package ru.example.ivan.smssender.ui.screens.new_message

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey

@Module
internal abstract class NewMessageFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun newMessageFragment(): NewMessageFragment

    @Binds
    @IntoMap
    @ViewModelKey(NewMessageViewModel::class)
    abstract fun bindNewMessageViewModel(viewModel: NewMessageViewModel): ViewModel


}
