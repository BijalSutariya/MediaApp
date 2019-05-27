package com.example.mediaapp.framework.dicomponents

import com.example.mediaapp.clientlayer.presentations.fragment.YtFragment
import com.example.mediaapp.viewmodellayer.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ViewModelModule::class])
@Singleton
interface ViewModelComponent {
    fun doInjection(ytFragment: YtFragment)
}