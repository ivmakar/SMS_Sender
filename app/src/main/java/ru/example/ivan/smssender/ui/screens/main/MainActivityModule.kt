package ru.example.ivan.smssender.ui.screens.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey

@Module
internal abstract class MainActivityModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel


}
