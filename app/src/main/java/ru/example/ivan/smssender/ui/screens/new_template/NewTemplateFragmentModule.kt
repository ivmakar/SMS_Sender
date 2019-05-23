package ru.example.ivan.smssender.ui.screens.new_template

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import ru.example.ivan.smssender.utility.di.ViewModelKey


@Module
internal abstract class NewTemplateFragmentModule {

    @ContributesAndroidInjector
    internal abstract fun templateFragment(): NewTemplateFragment

    @Binds
    @IntoMap
    @ViewModelKey(NewTemplateViewModel::class)
    abstract fun bindNewTemplateViewModel(viewModel: NewTemplateViewModel): ViewModel


}