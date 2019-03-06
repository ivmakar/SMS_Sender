package ru.example.ivan.smssender.ui.screens.chain

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey


@Module
internal abstract class ChainViewModule {

    @ContributesAndroidInjector
    internal abstract fun chainFragment(): ChainFragment

    @Binds
    @IntoMap
    @ViewModelKey(ChainViewModel::class)
    abstract fun bindChainViewModel(viewModel: ChainViewModel): ViewModel
}