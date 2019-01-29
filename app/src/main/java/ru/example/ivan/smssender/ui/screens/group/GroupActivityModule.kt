package ru.example.ivan.smssender.ui.screens.group

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey

@Module
internal abstract class GroupActivityModule {

    @ContributesAndroidInjector
    internal abstract fun groupActivity(): GroupActivity

    @Binds
    @IntoMap
    @ViewModelKey(GroupViewModel::class)
    abstract fun bindMainViewModel(viewModel: GroupViewModel): ViewModel

}