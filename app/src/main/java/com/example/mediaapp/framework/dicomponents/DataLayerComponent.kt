package com.example.mediaapp.framework.dicomponents

import com.example.mediaapp.domainlayer.datasources.database.DatabaseModule
import com.example.mediaapp.domainlayer.datasources.network.NetworkModule
import com.example.mediaapp.domainlayer.repositories.MediaRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, DatabaseModule::class])
@Singleton
interface DataLayerComponent {

    fun doInjection(mediaRepository: MediaRepository)
}