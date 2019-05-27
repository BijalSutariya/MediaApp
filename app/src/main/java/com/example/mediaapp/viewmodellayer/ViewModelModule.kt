package com.example.mediaapp.viewmodellayer

import com.example.mediaapp.domainlayer.repositories.MediaRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {
    internal val viewModelFactory: ViewModelFactory
    @Provides
    @Singleton
    get() = ViewModelFactory(MediaRepository())


}