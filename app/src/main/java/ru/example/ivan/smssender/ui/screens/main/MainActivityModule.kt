package ru.example.ivan.smssender.ui.screens.main

import dagger.Module
import dagger.Provides
import ru.example.ivan.smssender.data.ChainRepository

@Module
class MainActivityModule {

    @Provides
    fun provideChainViewModel(chainRepository: ChainRepository) : ChainViewModel =
        ChainViewModel(chainRepository)

    @Provides
    fun provideChainRepository() : ChainRepository =
            ChainRepository()
}
