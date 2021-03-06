package ru.example.ivan.smssender.ui.screens.template

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey


@Module
internal abstract class TemplateFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun templateFragment(): TemplateFragment

    @Binds
    @IntoMap
    @ViewModelKey(TemplateViewModel::class)
    abstract fun bindTemplateViewModel(viewModel: TemplateViewModel): ViewModel


}