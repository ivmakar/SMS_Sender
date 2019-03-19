package ru.example.ivan.smssender.ui.screens.new_group

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey

@Module
internal abstract class NewGroupFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun newGroupFragment(): NewGroupFragment

    @Binds
    @IntoMap
    @ViewModelKey(NewGroupViewModel::class)
    abstract fun bindNewGroupViewModel(viewModel: NewGroupViewModel): ViewModel


}
