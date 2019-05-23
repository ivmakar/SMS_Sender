package ru.example.ivan.smssender.ui.screens.group

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey

@Module
internal abstract class GroupFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun groupFragment(): GroupFragment

    @Binds
    @IntoMap
    @ViewModelKey(GroupViewModel::class)
    abstract fun bindMainViewModel(viewModel: GroupViewModel): ViewModel

}